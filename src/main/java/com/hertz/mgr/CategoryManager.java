/**
 * 
 */
package com.hertz.mgr;

import java.util.List;

import com.hertz.entity.Category;
import com.hertz.entity.Product;

/**
 * @author Subba Rao
 *
 */
public interface CategoryManager {

	/**
	 * Returns the category by the primary key
	 * 
	 * @param categoryId - id of the category
	 */
	public Category getCategory(Integer categoryId);
	
	public List<Category> getDescendants(Integer id, int dist);
	
	
	public List<Category> getSubordinates(Integer id);
	
	public List<Product> getProductsByCategoryId(Integer categoryId);
	
}
