/**
 * 
 */
package com.hertz.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


/**
 * @author Subba Rao Ch
 *
 */

@Entity
@Table(name = "CATEGORIES", uniqueConstraints = {@UniqueConstraint(columnNames = {"CATEGORY_NAME"})})
@SQLDelete(sql="UPDATE CATEGORIES SET IS_DELETED = 1 WHERE ID = ?")
@Where(clause="IS_DELETED = 0 OR IS_DELETED IS NULL")
public class Category implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8125925477803621740L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CAT_ID_SEQ")
	@SequenceGenerator(name = "CAT_ID_SEQ", sequenceName = "CAT_ID_SEQ")
	@Column(name = "ID", length = 11)
	private Integer id;
	
	@Column(name = "CATEGORY_NAME", length = 100)
	private String categoryName;
	
	@Column(name = "CATEGORY_IMAGE", length = 300)
	private String categoryImage;
	
	@Column(name = "CATEGORY_DESC", length = 500)
	private String categoryDesc;
	
	@Column(name = "SORT_ORDER", length = 11)
	private Integer sortOrder;
	
	@Column(name = "DATE_ADDED", length = 45)
	@Temporal(TemporalType.DATE)
	private Date dateAdded;
	
	@Column(name = "LAST_MODIFIED", length = 45)
	@Temporal(TemporalType.DATE)
	private Date lastModified;
	
	@Column(name = "IS_DELETED")
	private boolean deleted;
	
	@Column(name = "IS_TOP_CATEGORY")
	private boolean isTopCategory;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "PK_CAT_ID")
	@Where(clause = "IS_DELETED = 0 OR IS_DELETED IS NULL")
	private List<CatCatRel> catParentRel = new ArrayList<CatCatRel>();

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
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the categoryImage
	 */
	public String getCategoryImage() {
		return categoryImage;
	}

	/**
	 * @param categoryImage the categoryImage to set
	 */
	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}

	/**
	 * @return the categoryDesc
	 */
	public String getCategoryDesc() {
		return categoryDesc;
	}

	/**
	 * @param categoryDesc the categoryDesc to set
	 */
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	/**
	 * @return the sortOrder
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the dateAdded
	 */
	public Date getDateAdded() {
		return dateAdded;
	}

	/**
	 * @param dateAdded the dateAdded to set
	 */
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	/**
	 * @return the lastModified
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the catParentRel
	 */
	public List<CatCatRel> getCatParentRel() {
		return catParentRel;
	}

	/**
	 * @param catParentRel the catParentRel to set
	 */
	public void setCatParentRel(List<CatCatRel> catParentRel) {
		this.catParentRel = catParentRel;
	}

	/**
	 * @return the isTopCategory
	 */
	public boolean isTopCategory() {
		return isTopCategory;
	}

	/**
	 * @param isTopCategory the isTopCategory to set
	 */
	public void setTopCategory(boolean isTopCategory) {
		this.isTopCategory = isTopCategory;
	}

	}
