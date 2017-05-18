/**
 * 
 */
package com.hertz.mgr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hertz.entity.Category;
import com.hertz.entity.Product;
import com.hertz.mgr.CategoryManager;
import com.hertz.mgr.domain.CategoryDomainManager;

/**
 * @author Subba
 *
 */
@Component
public class CatgoryManagerImpl implements CategoryManager {

	@Autowired
	CategoryDomainManager domainManager;
	
	@Override
	public Category getCategory(Integer categoryId) {
		Category category = domainManager.getCategory(categoryId);
		return category;
	}

	
	 public List<Category> getDescendants(Integer id, int dist) {
		 List<Category> categoryList = domainManager.getDescendants(id, 0);
			return categoryList;
			 
	 }
	 
	 public List<Category> getSubordinates(Integer id) {
		 List<Category> categoryList = domainManager.getSubordinates(id);
			return categoryList;
	 }

	public List<Product> getProductsByCategoryId(Integer categoryId) {
		List<Product> productList = null;
		try {
			productList = domainManager.getCategoryCoupons(categoryId, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;
	}
	 
	 

}
