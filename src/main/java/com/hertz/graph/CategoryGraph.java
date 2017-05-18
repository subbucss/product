/**
 * 
 */
package com.hertz.graph;

import java.util.Date;

import com.hertz.graph.core.NamedGraphObject;
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
public class CategoryGraph {
	
	
	public static interface CategoryGraphManager extends NamedGraphObject {
		
		@Property("categoryId")
		public Integer getCategoryId();

		@Property("categoryId")
		public void setCategoryId(Integer categoryId);

		@Property("categoryName")
		public String getCategoryName();

		@Property("categoryName")
		public void setCategoryName(String categoryName);

		@Property("categoryImage")
		public String getCategoryImage();

		@Property("categoryImage")
		public void setCategoryImage(String categoryImage);

		@Property("categoryDesc")
		public String getCategoryDesc();

		@Property("categoryDesc")
		public void setCategoryDesc(String categoryDesc);

		@Property("categoryDateAdded")
		public Date getDateAdded();

		@Property("categoryDateAdded")
		public void setDateAdded(Date dateAdded);

		@Property("categoryLastModified")
		public Date getLastModified();

		@Property("categoryLastModified")
		public void setLastModified(Date lastModified);

				
		@Incidence(label = "parent")
		public Iterable<Parent> getParents();

		@Incidence(label = "parent")
		public Parent addParent(CategoryGraphManager parent);

		@Incidence(label = "parent")
		public void removeParent(Parent parent);

		@Incidence(label = "parent", direction = Direction.IN)
		public Iterable<SuperviseBy> getChilds();
		
		//Products
		@Incidence(label = "product")
		public ProductAssignment addProduct(CategoryProductGraph pos);

		@Incidence(label = "product")
		public Iterable<ProductAssignment> getProducts();

		@Incidence(label = "product")
		public void removeProductAssignment(ProductAssignment position);

		@Incidence(label = "product", direction = Direction.IN)
		public Iterable<ProductOccupied> getProductOccupied();	
	}
	
	/**
	 * Supervisor relationship for an Category. 
	 */
	public static interface Parent extends EdgeFrame {
		@Domain
		public CategoryGraphManager getChild();

		@Range
		public CategoryGraphManager getParent();

		@Property("categorySortOrder")
		public Integer getSortOrder();

		@Property("categorySortOrder")
		public void setSortOrder(Integer sortOrder);

	}
	
	/**
	 * Inverse of the supervisor relationship (points in the reverse direction)
	 */
	public static interface SuperviseBy extends EdgeFrame {
		@Domain
		public CategoryGraphManager getParent();

		@Range
		public CategoryGraphManager getChild();

		@Property("categorySortOrder")
		public Integer getSortOrder();

		@Property("categorySortOrder")
		public void setSortOrder(Integer sortOrder);
	}
	
	
	public static interface CategoryProductGraph extends NamedGraphObject {
		@Incidence(label = "product")
		public ProductAssignment addProduct(CategoryProductGraph pos);

		@Incidence(label = "product")
		public Iterable<ProductAssignment> getProduct();

		@Incidence(label = "product")
		public void removeProductAssignment(ProductAssignment position);

		@Property("productId")
		public Integer getProductId();

		@Property("productId")
		public void setProductId(Integer id);
	}

	public static interface ProductAssignment extends EdgeFrame {
		@Domain
		public CategoryGraphManager getCategory();

		@Range
		public CategoryProductGraph getProduct();

		@Property("startdate")
		public Date getStartdate();

		@Property("startdate")
		public void setStartdate(Date startdate);

		@Property("enddate")
		public Date getEnddate();

		@Property("enddate")
		public void setEnddate(Date enddate);
	}

	public static interface ProductOccupied extends EdgeFrame {
		@Domain
		public CategoryProductGraph getProduct();

		@Range
		public CategoryGraphManager getCategory();

		@Property("startdate")
		public Date getStartdate();

		@Property("startdate")
		public void setStartdate(Date startdate);

		@Property("enddate")
		public Date getEnddate();

		@Property("enddate")
		public void setEnddate(Date enddate);
	}

}
