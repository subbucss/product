/**
 * 
 */
package com.hertz.mgr.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.hertz.cache.Cache;
import com.hertz.cache.CacheLoader;
import com.hertz.entity.CatCatRel;
import com.hertz.entity.Category;
import com.hertz.entity.Product;
import com.hertz.entity.ProductCategoryRelationship;
import com.hertz.graph.CategoryGraph;
import com.hertz.graph.CategoryGraph.CategoryGraphManager;
import com.hertz.graph.CategoryGraphRelationship;
import com.hertz.graph.CategoryProductGraphRelationship;
import com.hertz.repository.CategoryRepository;
import com.hertz.repository.dao.CategoryDao;
import com.hertz.repository.dao.ProductDao;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.frames.FramedGraph;

/**
 * @author Subba Rao Ch
 *
 */

@Component
public class CategoryDomainManager  implements CacheLoader<Integer, Category>  {

	@Autowired
	private CategoryDao dataService;
	
	@Autowired
	private ProductDao productDao = null;

	@Autowired
	private ProductDomainManager productManager = null;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	
	private static final Logger log = Logger.getLogger(CategoryDomainManager.class);

	@Autowired
	@Qualifier("CatCache")
	private Cache<Integer, Category> cache;
	
	private Graph graph = null;
	private FramedGraph<Graph> framedGraph = null;
	private CategoryGraphRelationship categoryRelationship = null;
	private CategoryProductGraphRelationship categoryProductGraph = null;
	
	
	public CategoryDomainManager() {
		graph = new TinkerGraph();
		framedGraph = new FramedGraph<Graph>(graph);
		categoryRelationship = new CategoryGraphRelationship(framedGraph);
		categoryProductGraph = new CategoryProductGraphRelationship(framedGraph);
	}
	
	@PostConstruct
	public void reloadAllGraphs() {
		
		TinkerGraph tempGraph = (TinkerGraph) graph;
		synchronized (CategoryDomainManager.class) {
			tempGraph.clear();
			reloadParentRelationships();
			reloadCatProductRelationships();
		}
	}
	
	/**
	 * reloads the supervisor relationships from the database to graph object
	 */
	public void reloadParentRelationships() {
		try {
			List<CatCatRel> result = dataService.getParentRelationships();


			if (result != null && !result.isEmpty()) {

				for (CatCatRel iterator : result) {
					addParentInGraph(iterator);
				}
			}
		} catch (Exception e) {
			log.error("Exception in Manager - reloadSupervisorRelationship() : ", e);
		}
	}
	
	 public Category getCategory(Integer categoryId) {
		 log.info("Category Id:" +categoryId);
		 Category category = getCache().get(categoryId, this);
     return category;
     }
	 
	 /**
	     * Loads the data from DAC. This is called by the cache when the Category
	     * is not found in cache.
	     * 
	     * @param key Key to lookup the Category 
	     * @return Category object
	     */
	    public Category load(Integer categoryId) {
	    	 log.info("Load Category Id:" +categoryId);
	    	Category category = categoryRepository.findOne(categoryId);
	        return category;
	    }
	 
	    
	    /**
		 * adds supervisor relationship into the graph
		 */
		public void addParentInGraph(CatCatRel data) {
			try {
				CategoryGraph.CategoryGraphManager category = categoryRelationship.add(data.getCat().getId(), CategoryGraph.CategoryGraphManager.class);
				category.setCategoryId(data.getCat().getId());
				category.setCategoryDesc(data.getCat().getCategoryDesc());
				category.setCategoryImage(data.getCat().getCategoryImage());
				category.setCategoryName(data.getCat().getCategoryName());
				category.setDateAdded(data.getCat().getDateAdded());
				category.setLastModified(data.getCat().getLastModified());
				
				
				CategoryGraph.CategoryGraphManager parent = categoryRelationship.add(data.getCatParent().getId(), CategoryGraph.CategoryGraphManager.class);
				parent.setCategoryId(data.getCatParent().getId());
				parent.setCategoryDesc(data.getCatParent().getCategoryDesc());
				parent.setCategoryImage(data.getCatParent().getCategoryImage());
				parent.setCategoryName(data.getCatParent().getCategoryName());
				parent.setDateAdded(data.getCatParent().getDateAdded());
				parent.setLastModified(data.getCatParent().getLastModified());
				categoryRelationship.addParent(data);
			} catch (Exception e) {
				log.error("Exception in Manager - addSupervisorInGraph() : ", e);
			}
		}
		
		
		/**
	     * Returns a flat list of all children and children's children for this
	     * Category
	     *
	     * @param id Category ID
	     * @return List of descendants of the Categories
	     */
	    public List<Category> getDescendants(Integer id, int dist) {
	        List<CategoryGraph.CategoryGraphManager> catGrapths = getCategoryRelationship().getDescendants(id, dist);
	        return toCategoryList(catGrapths);
	    }
	    
	    /**
	     * Returns a flat list of all children and children's children for this
	     * Category
	     *
	     * @param id Category ID
	     * @return List of descendants of the Categories
	     */
	    public List<Category> getSubordinates(Integer id) {
	        List<CategoryGraph.CategoryGraphManager> catGrapths = getCategoryRelationship().getSubordinates(id);
	        return toCategoryList(catGrapths);
	    }
	 
	 
	    /**
	     * Converts a list of graph Category objects 
	     *
	     * @param category List of graph Category objects
	     * @return List of Categories
	     */
	    private List<Category> toCategoryList(List<CategoryGraph.CategoryGraphManager> emps) {
	        List<Category> catList = new ArrayList<Category>();
	        
	        if (emps != null && !emps.isEmpty()) {
		        for (CategoryGraph.CategoryGraphManager emp : emps) {
		            catList.add(toCategory(emp));
		        }
	        }
	        
	        return catList;
	    }
	    
	    /**
	     * Converts an individual Category graph object to Category
	     *
	     * @param Category Graph to Category object
	     * @return Category
	     */
		private Category toCategory(CategoryGraph.CategoryGraphManager categoryGraph) {
			Category categoryData = new Category();
			
			if (categoryGraph != null) {
				categoryData.setId(categoryGraph.getCategoryId());
				categoryData.setCategoryName(categoryGraph.getCategoryName());
				categoryData.setCategoryDesc(categoryGraph.getCategoryDesc());
				categoryData.setCategoryImage(categoryGraph.getCategoryImage());
				categoryData.setDateAdded(categoryGraph.getDateAdded());
				categoryData.setLastModified(categoryGraph.getLastModified());
			}
			
			return categoryData;
		}


		 /**
		 * reloads the supervisor relationships from the database to graph object
		 */
		public void reloadCatProductRelationships() {
			try {
				List<ProductCategoryRelationship> result = productDao.getProductCatRelationships();


				if (result != null && !result.isEmpty()) {

					for (ProductCategoryRelationship iterator : result) {
						addProductInGraph(iterator);
					}
				}
			} catch (Exception e) {
				log.error("Exception in CategoryManager - reloadSupervisorRelationship() : ", e);
			}
		}
		
		 /**
		 * adds supervisor relationship into the graph
		 */
		public void addProductInGraph(ProductCategoryRelationship data) {
			try {
				CategoryGraph.CategoryProductGraph couponGraph = categoryProductGraph.add(data.getProductBase().getId(), CategoryGraph.CategoryProductGraph.class);
				couponGraph.setProductId(data.getId());
				categoryProductGraph.addProductToCategory(data);
			} catch (Exception e) {
				log.error("Exception in EmployeeManager - addSupervisorInGraph() : ", e);
			}
		}
		
		
		 public List<Product> getCategoryCoupons(Integer id, int dist) {
		        List<CategoryGraph.CategoryGraphManager> catGrapths = getCategoryRelationship().getDescendants(id, dist);
		        if(catGrapths == null){
		        	catGrapths = new ArrayList<CategoryGraph.CategoryGraphManager>();
		        }
		        CategoryGraph.CategoryGraphManager cat = getCategoryRelationship().get(id, CategoryGraphManager.class);
		        if(cat != null){
		        catGrapths.add(cat);
		        }
		        
		        List<Product> catList = new ArrayList<Product>();
		        Set<Integer> couponIdSet = toCouponList(catGrapths);
		    	if(couponIdSet != null && !couponIdSet.isEmpty()){
	      		catList = productManager.getProductsByIds(couponIdSet);	
	      		}
		        
		        return catList;
		    }
		
		 /**
		     * Converts a list of graph Coupon objects 
		     *
		     * @param Coupon List of graph Coupon objects
		     * @return List of Coupon
		     */
		    private Set<Integer> toCouponList(List<CategoryGraph.CategoryGraphManager> cats) {
		        Set<Integer> couponIds = new HashSet<Integer>();
		        if (cats != null && !cats.isEmpty()) {
			        for (CategoryGraph.CategoryGraphManager cat : cats) {
			            if(cat.getProducts() != null){
			            	Iterable<CategoryGraph.ProductAssignment> itr = cat.getProducts();
			            	//Iterator<CategoryGraph.CouponAssignment> couItr= itr.iterator();
			            	for (Iterator<CategoryGraph.ProductAssignment> iter = itr.iterator(); iter.hasNext(); ) {
			            		CategoryGraph.ProductAssignment element = iter.next();
			            		if(element.getProduct().getProductId() != null){
			            		couponIds.add(element.getProduct().getProductId());
			            		}
			            	}
			            }
			        }
		        }
		        return couponIds;
		    }
		 
	/**
	 * @return the cache
	 */
	public Cache<Integer, Category> getCache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(Cache<Integer, Category> cache) {
		this.cache = cache;
	}

	public void removeCache(Integer key)
	{
		cache.remove(key);
	}

		
	/**
	 * @return the categoryRelationship
	 */
	public CategoryGraphRelationship getCategoryRelationship() {
		return categoryRelationship;
	}


	/**
	 * @param categoryRelationship the categoryRelationship to set
	 */
	public void setCategoryRelationship(
			CategoryGraphRelationship categoryRelationship) {
		this.categoryRelationship = categoryRelationship;
	}
}
