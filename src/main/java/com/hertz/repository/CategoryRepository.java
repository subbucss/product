/**
 * 
 */
package com.hertz.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hertz.entity.Category;

/**
 * @author Subba Rao Ch
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	public List<Category> findAllByIsTopCategoryOrderBySortOrder(boolean isTopCategory);

}
