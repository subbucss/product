/**
 * 
 */
package com.hertz.graph;

import com.hertz.entity.ProductCategoryRelationship;
import com.hertz.graph.CategoryGraph.CategoryGraphManager;
import com.hertz.graph.CategoryGraph.CategoryProductGraph;
import com.hertz.graph.ProductGraph.ProductGraphManager;
import com.hertz.graph.core.BaseRelationship;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.frames.FramedGraph;

/**
 * @author Subba Rao Ch
 *
 */
public class CategoryProductGraphRelationship extends BaseRelationship {

	
	public CategoryProductGraphRelationship(FramedGraph<Graph> framedGraph) {
		super(framedGraph);
	}


	/**
	 * Adds a relationship between the given Product and the Category
	 * 
	 * @param relData Product Category relationship data
	 */
	public void addProductToCategory(ProductCategoryRelationship relData) {
		if (isAvailable(relData.getCategoryId()) && isProductAvailable(relData.getProductBase().getId())) {
			CategoryProductGraph product = get(relData.getProductBase().getId(), CategoryProductGraph.class);
			CategoryGraphManager category = get(relData.getCategoryId(), CategoryGraphManager.class);
			category.addProduct(product);
		}
	}
	
	
	public boolean isAvailable(Integer id) {
		try {
			CategoryGraphManager cat = get(id, CategoryGraphManager.class);

			if (id.equals(cat.getCategoryId())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public boolean isProductAvailable(Integer id) {
		try {
			ProductGraphManager org = get(id, ProductGraphManager.class);

			if (id.equals(org.getProductId())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
}
