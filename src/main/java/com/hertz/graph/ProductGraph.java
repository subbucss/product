/**
 * 
 */
package com.hertz.graph;

import java.util.Date;

import com.hertz.graph.CategoryGraph.CategoryGraphManager;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.Domain;
import com.tinkerpop.frames.EdgeFrame;
import com.tinkerpop.frames.Incidence;
import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.Range;

/**
 * @author Subba Rao Ch
 *
 */
public class ProductGraph {

	public static interface ProductGraphManager extends NamedGraphObject {
		
		@Incidence(label = "assignedto")
		public ProductAssignment addProduct(ProductGraphManager productManager);

		@Incidence(label = "assignedto")
		public Iterable<ProductAssignment> getProduct();

		@Incidence(label = "assignedto")
		public void removeProductAssignment(ProductAssignment product);

		@Incidence(label = "assignedto", direction = Direction.IN)
		public Iterable<ProductCategory> getAssignedCategory();

		@Property("productId")
		public Integer getProductId();

		@Property("productId")
		public void setProductId(Integer id);
		
		@Property("productTitle")
		public String getProductTitle();
		
		@Property("productTitle")
		public void setProductTitle(String productTitle);
		
		@Property("shortDescription")
		public String getShortDescription();
		
		@Property("shortDescription")
		public void setShortDescription(String shortDescription);
		
		@Property("productType")
		public String getProductType();
		
		@Property("productType")
		public void setProductType(String productType);
		
		@Property("productCode")
		public String getProductCode();
		
		@Property("productCode")
		public void setProductCode(String productCode);
		
		@Property("productCategory")
		public Integer getProductCategory();
		
		@Property("productCategory")
		public void setProductCategory(Integer productCategory);
		
		@Property("startDate")
		public Date getStartDate();
		
		@Property("startDate")
		public void setStartDate(Date startDate);
		
		@Property("endDate")
		public Date getEndDate();
		
		@Property("endDate")
		public void setEndDate(Date endDate);
		
		@Property("imageName")
		public String getImageName();
		
		@Property("imageName")
		public void setImageName(String imageName);
		
		@Property("isActive")
		public Integer getIsActive();
		
		@Property("isActive")
		public void setIsActive(Integer isActive);
		
		@Property("productTags")
		public String getProductTags();
		
		@Property("productTags")
		public void setProductTags(String productTags);
	}

	public static interface ProductAssignment extends EdgeFrame {
		@Domain
		public CategoryGraphManager getCategory();

		@Range
		public ProductGraphManager getProduct();

	}
	
	
	public static interface ProductCategory extends EdgeFrame {
		@Domain
		public ProductGraphManager getProduct();

		@Range
		public CategoryGraphManager getCategory();
		
	}
	
}
