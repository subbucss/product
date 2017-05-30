/**
 * 
 */
package com.hertz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * @author Subba Rao Ch
 *
 */
@Entity
@Table(name = "cat_product_rel")
@SQLDelete(sql="UPDATE cat_product_rel SET IS_DELETED = 1 WHERE ID = ?")
@Where(clause="IS_DELETED = 0 OR IS_DELETED IS NULL")
public class ProductCategoryRelationship implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8731657330513536746L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PRD_CAT_REL_ID_SEQ")
	@SequenceGenerator(name = "PRD_CAT_REL_ID_SEQ", sequenceName = "PRD_CAT_REL_ID_SEQ")
	@Column(name = "ID", length = 11)
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name = "PK_PRD_ID", referencedColumnName = "ID", nullable = false)
	private ProductBase productBase;

	
	@Column (name = "PK_CAT_ID", length = 11, nullable = false)
	private Integer categoryId;
	
	@Column(name = "IS_DELETED", length = 10)
	private Integer isDeleted;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the productBase
	 */
	public ProductBase getProductBase() {
		return productBase;
	}

	/**
	 * @param productBase the productBase to set
	 */
	public void setProductBase(ProductBase productBase) {
		this.productBase = productBase;
	}

	/**
	 * @return the categoryId
	 */
	public Integer getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the isDeleted
	 */
	public Integer getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	 

	}
