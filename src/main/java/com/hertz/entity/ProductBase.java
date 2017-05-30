/**
 * 
 */
package com.hertz.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Where;

/**
 * @author Subba Rao Ch
 *
 */
@Entity
@Immutable
@Table(name = "product_master", uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
@Where(clause="IS_DELETED = 0 OR IS_DELETED IS NULL")
public class ProductBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 487328376451511202L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PRODUCT_ID_SEQ")
	@SequenceGenerator(name = "PRODUCT_ID_SEQ", sequenceName = "PRODUCT_ID_SEQ")
	@Column(name = "ID", length = 11)
	private Integer id;
	
	@Column(name = "PRODUCT_TITLE", length = 200)
	private String title;
	
	@Column(name = "SHORT_DESCRIPTION", length = 500)
	private String shortDescription;
	
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
	
	@Column(name = "PRODUCT_TAGS", length = 300)
	private String productTags;

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
}