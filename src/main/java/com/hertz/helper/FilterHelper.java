/**
 * 
 */
package com.hertz.helper;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Subba Rao Ch
 *
 */
public class FilterHelper {
	
	private Set<Integer> categoryIds = new HashSet<Integer>();
	
	private Set<Integer> merchantIds = new HashSet<Integer>();
	
	private String searchStr;
	
	private String orderBy;
	
	private Integer pageNum;
	
	private Integer pageLimit;
	

	/**
	 * @return the categoryIds
	 */
	public Set<Integer> getCategoryIds() {
		return categoryIds;
	}

	/**
	 * @param categoryIds the categoryIds to set
	 */
	public void setCategoryIds(Set<Integer> categoryIds) {
		this.categoryIds = categoryIds;
	}

	/**
	 * @return the merchantIds
	 */
	public Set<Integer> getMerchantIds() {
		return merchantIds;
	}

	/**
	 * @param merchantIds the merchantIds to set
	 */
	public void setMerchantIds(Set<Integer> merchantIds) {
		this.merchantIds = merchantIds;
	}

	/**
	 * @return the searchStr
	 */
	public String getSearchStr() {
		return searchStr;
	}

	/**
	 * @param searchStr the searchStr to set
	 */
	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the pageNum
	 */
	public Integer getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the pageLimit
	 */
	public Integer getPageLimit() {
		return pageLimit;
	}

	/**
	 * @param pageLimit the pageLimit to set
	 */
	public void setPageLimit(Integer pageLimit) {
		this.pageLimit = pageLimit;
	}
	
}
