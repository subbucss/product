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
@Table(name = "PRODUCT_MASTER", uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
@SQLDelete(sql="UPDATE PRODUCT_MASTER SET IS_DELETED = 1 WHERE ID = ?")
@Where(clause="IS_DELETED = 0 OR IS_DELETED IS NULL")
public class Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -848800876811646724L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PRODUCT_ID_SEQ")
	@SequenceGenerator(name = "PRODUCT_ID_SEQ", sequenceName = "PRODUCT_ID_SEQ")
	@Column(name = "ID", length = 11)
	private Integer id;
	
	@Column(name = "PRODUCT_TITLE", length = 200)
	private String title;
	
	@Column(name = "SHORT_DESCRIPTION", length = 500)
	private String shortDescription;
	
	@Column(name = "LONG_DESCRIPTION", length = 3000)
	private String longDescription;
	
	@Column(name = "PRODUCT_TYPE", length = 50)
	private String type;
	
	@Column(name = "PRODUCT_CODE", length = 100)
	private String code;
	
	@Column(name = "PRODUCT_CATEGORY", length = 10)
	private Integer category;
	
	@Column(name = "START_DATE", length = 45)
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "END_DATE", length = 45)
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Column(name = "IMAGE_NAME", length = 300)
	private String imageName;
	
	@Column(name = "IS_ACTIVE", length = 10)
	private Integer isActive;
	
	@Column(name = "IS_DELETED", length = 10)
	private Integer isDeleted;
	
	@Column(name = "DATE_ADDED", length = 45)
	@Temporal(TemporalType.DATE)
	private Date dateAdded;
	
	@Column(name = "DATE_UPDATED", length = 45)
	@Temporal(TemporalType.DATE)
	private Date dateUpdated;
	
	@Column(name = "IS_TOP_DEAL", length = 11)
	private Integer isTopDeal;
	
	@Column(name = "PRODUCT_TAGS", length = 300)
	private String productTags;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "PK_PRD_ID")
	private List<ProductCategoryRelationship> couponCategoryRel = new ArrayList<ProductCategoryRelationship>();
	

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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the shortDescription
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * @param shortDescription the shortDescription to set
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	/**
	 * @return the longDescription
	 */
	public String getLongDescription() {
		return longDescription;
	}

	/**
	 * @param longDescription the longDescription to set
	 */
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * @param imageName the imageName to set
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * @return the isActive
	 */
	public Integer getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
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
	 * @return the dateUpdated
	 */
	public Date getDateUpdated() {
		return dateUpdated;
	}

	/**
	 * @param dateUpdated the dateUpdated to set
	 */
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	/**
	 * @return the isTopDeal
	 */
	public Integer getIsTopDeal() {
		return isTopDeal;
	}

	/**
	 * @param isTopDeal the isTopDeal to set
	 */
	public void setIsTopDeal(Integer isTopDeal) {
		this.isTopDeal = isTopDeal;
	}

	/**
	 * @return the productTags
	 */
	public String getProductTags() {
		return productTags;
	}

	/**
	 * @param productTags the productTags to set
	 */
	public void setProductTags(String productTags) {
		this.productTags = productTags;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	}
