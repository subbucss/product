/**
 * 
 */
package com.hertz.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hertz.entity.Product;

/**
 * @author Subba Rao Ch
 *
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {
	public List<Product> findAllByIsTopDealOrderByDateUpdatedDesc(int isTopDeal);

}
