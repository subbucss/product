/**
 * 
 */
package com.hertz.util;

import java.util.ArrayList;
import java.util.List;

import com.hertz.entity.Category;

/**
 * @author Subba Rao Ch
 *
 */
public class CategoryHierarchy {
	private Category category;
	private List<CategoryHierarchy> categoryChildList = new ArrayList<CategoryHierarchy>();
	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	/**
	 * @return the categoryChildList
	 */
	public List<CategoryHierarchy> getCategoryChildList() {
		return categoryChildList;
	}
	/**
	 * @param categoryChildList the categoryChildList to set
	 */
	public void setCategoryChildList(List<CategoryHierarchy> categoryChildList) {
		this.categoryChildList = categoryChildList;
	}
	
	

}
