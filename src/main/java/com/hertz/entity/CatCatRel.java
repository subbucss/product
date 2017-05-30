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
@Table(name = "cat_rel_cat_parent")
@SQLDelete(sql="UPDATE cat_rel_cat_parent SET IS_DELETED = 1 WHERE ID = ?")
@Where(clause="IS_DELETED = 0 OR IS_DELETED IS NULL")
public class CatCatRel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2111739231729724745L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CAT_REL_ID_SEQ")
	@SequenceGenerator(name = "CAT_REL_ID_SEQ", sequenceName = "CAT_REL_ID_SEQ")
	@Column(name = "ID", length = 11)
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name = "PK_CAT_ID", referencedColumnName = "ID")
	private Category cat;

	@ManyToOne
	@JoinColumn(name = "CAT_PARENT_ID", referencedColumnName = "ID", nullable = false)
	private Category catParent;
	
	 @Column(name = "IS_DELETED")
	 private boolean deleted;

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
	 * @return the catParent
	 */
	public Category getCatParent() {
		return catParent;
	}

	/**
	 * @param catParent the catParent to set
	 */
	public void setCatParent(Category catParent) {
		this.catParent = catParent;
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
	 * @return the cat
	 */
	public Category getCat() {
		return cat;
	}

	/**
	 * @param cat the cat to set
	 */
	public void setCat(Category cat) {
		this.cat = cat;
	}
}
