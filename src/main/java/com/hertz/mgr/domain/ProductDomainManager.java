/**
 * 
 */
package com.hertz.mgr.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.hertz.cache.Cache;
import com.hertz.cache.CacheLoader;
import com.hertz.entity.Product;
import com.hertz.helper.FilterHelper;
import com.hertz.repository.ProductRepository;
import com.hertz.repository.dao.ProductDao;


/**
 * @author Subba Rao Ch
 *
 */

@Component
public class ProductDomainManager implements CacheLoader<Integer, Product>  {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	ProductRepository repository;
	
	private static final Logger log = Logger.getLogger(ProductDomainManager.class);

	@Autowired
	@Qualifier("PrdCache")
	private Cache<Integer, Product> cache;
    
    
   
    
	 public Product load(Integer productId) {
    	 log.info("Load Coupon Id:" +productId);
    	 Product coupon = repository.findOne(productId);
        return coupon;
     }
	 
	 
	 	 
	 public Product getProductById(Integer couponId) {
	   Product couponData = getCache().get(couponId, this);
        return couponData;
    }

	 
	 public List<Product> getProductsByIds(Set<Integer> couponIds) {
		 Set<Integer> nonCacheList = new java.util.HashSet<Integer>();
		 List<Product> couponList = new ArrayList<Product>();
 		 for(Integer couponId : couponIds){
		   Product couponData = getCache().get(couponId, this);
		   if(couponData == null){
			   nonCacheList.add(couponId);
		   }else{
			   couponList.add(couponData);
		   }
		 }
 		 
 		 if(nonCacheList != null && !nonCacheList.isEmpty()){
 			 List<Product> coupons = repository.findAll(nonCacheList);
 			couponList.addAll(coupons);
 		 }
	      return couponList;
	    }

		
		/**
		 * Fetch search results by search string
		 * @return
		 */
	 @SuppressWarnings("unchecked")
		public Map<String, Object> productSearch(FilterHelper filter){
		 List<Product> products = null;
			Map<String, Object> searchResult = productDao.productSearch(filter);
			Set<Integer> prdList = (Set<Integer>)searchResult.get("productList");
			
			if(prdList != null && !prdList.isEmpty()){
				products = getProductsByIds(prdList);
			}
			
			if(searchResult != null){
				searchResult.put("products", products);
			}
			return searchResult;
		}
		
		/**
		 * Fetch search results by search string
		 * @return
		 */
		public Map<String, Object> productSearchByFilter(FilterHelper filter){
			return productDao.productSearchByFilter(filter);
		}
		
		
		
	 /**
	 * @return the cache
	 */
	public Cache<Integer, Product> getCache() {
		return cache;
	}



	/**
	 * @param cache the cache to set
	 */
	public void setCache(Cache<Integer, Product> cache) {
		this.cache = cache;
	}
	
}
