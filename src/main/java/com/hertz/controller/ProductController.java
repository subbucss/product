/**
 * 
 */
package com.hertz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hertz.entity.Product;
import com.hertz.repository.ProductRepository;

/**
 * @author Subba Rao Ch
 *
 */
@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;

	
	@RequestMapping("/all")
	public List<Product> listOrders(){
		return productRepository.findAll();
	}

	@RequestMapping("/{id}")
	public Product getById(@PathVariable Integer id){
		return productRepository.findOne(id);
	}
	
	@RequestMapping("/topDelas")
	public List<Product> getTopDeals(){
		return productRepository.findAllByIsTopDealOrderByDateUpdatedDesc(1);
	}
	
}
