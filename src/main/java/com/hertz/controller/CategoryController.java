/**
 * 
 */
package com.hertz.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hertz.entity.Category;
import com.hertz.entity.Product;
import com.hertz.mgr.CategoryManager;
import com.hertz.repository.CategoryRepository;
import com.hertz.util.CategoryHierarchy;

/**
 * @author Subba Rao Ch
 *
 */
@RestController
@RequestMapping("/product/category")
public class CategoryController {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	CategoryManager categoryMgr;
	
	@RequestMapping("/all")
	public List<Category> listCategories(){
		return categoryRepository.findAll();
	}

	@RequestMapping("/topCategories")
	public List<Category> findAllByIsTopCategoryOrderBySortOrder(){
		return categoryRepository.findAllByIsTopCategoryOrderBySortOrder(true);
		
	}
	
	
	@RequestMapping("/topCategoriesTree")
	public List<CategoryHierarchy> topCategoriesTree(){
		List<CategoryHierarchy> catTree = new ArrayList<CategoryHierarchy>();
		List<Category> catList = categoryRepository.findAllByIsTopCategoryOrderBySortOrder(true);
		if(catList != null && !catList.isEmpty()){
		for(Category cat:catList){
		CategoryHierarchy catHier = null;
		Category category = categoryMgr.getCategory(cat.getId());
		if(category != null){
			catHier = getAllChildCategories(category);
			catTree.add(catHier);
		}
		}
		}
		return catTree;
	}
	
	@RequestMapping("/{id}")
	public Category getById(@PathVariable Integer id){
		return categoryMgr.getCategory(id);
	}
	
	/**
	 * Returns the All child Categories object with the given Category Id
	 * 
	 * @param categoryId ID of the Category 
	 * @return CategoryHierarchy for the ID
	 */
	@RequestMapping(value = "/categoryTree/{categoryId}")
	private CategoryHierarchy getChildCategories(@PathVariable(value = "categoryId") int categoryId){
		CategoryHierarchy catHier = null;
		Category category = categoryMgr.getCategory(categoryId);
		if(category != null){
			catHier = getAllChildCategories(category);
		}
		return catHier;
	}
	
	
	private CategoryHierarchy getAllChildCategories(Category parent){
		CategoryHierarchy orgHier=new CategoryHierarchy();
		List<Category> childList = categoryMgr.getSubordinates(parent.getId());
		orgHier.setCategory(parent);
    	for(int j=0;j<childList.size();j++) {
    		orgHier.getCategoryChildList().add(getAllChildCategories(childList.get(j)));
    	}
		return orgHier;
	}
	
	
	@RequestMapping(value = "/categoryProducts/{categoryId}")
	public List<Product> getCategoryProducts(@PathVariable(value = "categoryId") int categoryId) {
		List<Product> productList = categoryMgr.getProductsByCategoryId(categoryId);
		return productList;
	}
	
	/*@RequestMapping("/prodcuctSearch")
	public List<Product> productSearch(){
		return categoryMgr.productSearch();
	}*/
	
	
}
