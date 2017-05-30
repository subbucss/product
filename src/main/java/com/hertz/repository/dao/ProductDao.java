/**
 * 
 */
package com.hertz.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hertz.entity.ProductBase;
import com.hertz.entity.ProductCategoryRelationship;
import com.hertz.helper.CategoryCountHelper;
import com.hertz.helper.FilterHelper;


/**
 * @author Subba Rao Ch
 *
 */
@Component
public class ProductDao{

	@Autowired
	JdbcTemplate jdbcTemplate = null;
	int pageLimit = 50;
	
	public Map<String, Object> productSearch(FilterHelper filter){
		HashMap<String, Object> resultsMap = new HashMap<String, Object>();
		try{
		Calendar date = Calendar.getInstance();
		Timestamp timestamp = new Timestamp(date.getTimeInMillis());
		
		String sql = " FROM PRODUCT_MASTER PM LEFT OUTER JOIN  CAT_PRODUCT_REL CCR ON PM.ID=CCR.PK_PRD_ID AND (CCR.IS_DELETED = 0 OR CCR.IS_DELETED IS NULL) "+
					 " LEFT OUTER JOIN CATEGORIES CAT ON CAT.ID = CCR.PK_CAT_ID AND (CAT.IS_DELETED = 0 OR CAT.IS_DELETED IS NULL) "
					 + "WHERE PM.START_DATE <= ? AND (PM.END_DATE >= ? OR PM.END_DATE IS NULL) ";
		List<Object> inputs = new ArrayList<Object>();
		inputs.add(timestamp);
		inputs.add(timestamp);
		
		if(filter.getSearchStr() != null && !filter.getSearchStr().isEmpty()){
			sql += " AND PM.PRODUCT_TAGS LIKE '%"+filter.getSearchStr()+"%' ";
		}
		
		String orderBy = "";
		if(filter.getOrderBy() != null && !filter.getOrderBy().isEmpty()){
			if ("date".equalsIgnoreCase(filter.getOrderBy())) {
				orderBy = " ORDER BY PM.DATE_ADDED DESC ";
			} /*else if ("hits".equalsIgnoreCase(filter.getOrderBy())) {
				sort = " ORDER BY PM.DATE_ADDED DESC ";
			}*/
		}else{
			orderBy = " ORDER BY PM.DATE_ADDED DESC ";
		}
		
		String limit = " LIMIT ";
		int[] pageLimits = pagination( filter.getPageNum(), filter.getPageLimit());
		limit = limit + " "+pageLimits[0]+","+pageLimits[1]+" ";
		
		Set<Integer> productList = getProductsBySearch(sql, inputs, orderBy, limit);
		resultsMap.put("productList", productList);
		System.out.println("productList===>"+productList);
		
		Integer rowCount = getProductSearchCount(sql, inputs);
		resultsMap.put("searchCount", rowCount);
		System.out.println("rowCount===>"+rowCount);
		
		List<CategoryCountHelper> countBycategory = getCountByCategory(sql, inputs, orderBy);
		resultsMap.put("countByCategory", countBycategory);
		//System.out.println("countBycategory===>"+countBycategory);
		
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(" Exception in CouponDao class and couponSearch() ");
		}
		return resultsMap;
	}
	
	
	public Map<String, Object> productSearchByFilter(FilterHelper filter){
		HashMap<String, Object> resultsMap = new HashMap<String, Object>();
		try{
		Calendar date = Calendar.getInstance();
		Timestamp timestamp = new Timestamp(date.getTimeInMillis());
		
		String sql = " FROM PRODUCT_MASTER PM LEFT OUTER JOIN  CAT_PRODUCT_REL CCR ON PM.ID=CCR.PK_PRD_ID AND (CCR.IS_DELETED = 0 OR CCR.IS_DELETED IS NULL) "+
					 " LEFT OUTER JOIN CATEGORIES CAT ON CAT.ID = CCR.PK_CAT_ID AND (CAT.IS_DELETED = 0 OR CAT.IS_DELETED IS NULL) "
					 + "WHERE PM.START_DATE <= ? AND (PM.END_DATE >= ? OR PM.END_DATE IS NULL) ";
		List<Object> inputs = new ArrayList<Object>();
		inputs.add(timestamp);
		inputs.add(timestamp);
		
		
		if(filter.getCategoryIds() != null && !filter.getCategoryIds().isEmpty()){
			String catIds = "";
			for(Integer catId : filter.getCategoryIds()){
				if(catIds.equals("")){
					catIds += catId+"";
				}else{
					catIds += ","+catId;
				}
				
			}
			sql += " AND CCR.PK_CAT_ID IN ("+catIds+") ";
		}
		
		
		if(filter.getSearchStr() != null && !filter.getSearchStr().isEmpty()){
			sql += " AND PM.PRODUCT_TAGS LIKE '%"+filter.getSearchStr()+"%' ";
		}
		
		String orderBy = "";
		if(filter.getOrderBy() != null && !filter.getOrderBy().isEmpty()){
			if ("date".equalsIgnoreCase(filter.getOrderBy())) {
				orderBy = " ORDER BY PM.DATE_ADDED DESC ";
			} /*else if ("hits".equalsIgnoreCase(filter.getOrderBy())) {
				sort = " ORDER BY PM.DATE_ADDED DESC ";
			}*/
		}else{
			orderBy = " ORDER BY PM.DATE_ADDED DESC ";
		}
		
		String limit = " LIMIT ";
		int[] pageLimits = pagination( filter.getPageNum(), filter.getPageLimit());
		limit = limit + " "+pageLimits[0]+","+pageLimits[1]+" ";
		
		Set<Integer> couponList = getProductsBySearch(sql, inputs, orderBy, limit);
		resultsMap.put("couponList", couponList);
		//System.out.println("couponList===>"+couponList);
		
		Integer rowCount = getProductSearchCount(sql, inputs);
		resultsMap.put("searchCount", rowCount);
		//System.out.println("rowCount===>"+rowCount);
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(" Exception in CouponDao class and couponSearch() ");
		}
		return resultsMap;
	}

	private int[] pagination(Integer pageNumIn, Integer pageLimitIn){
		int[] pagination = new int[2];
		int pageNum = 0;
		if(pageLimitIn != null && pageLimitIn != 0){
			pageLimit = pageLimitIn;
		}
		if(pageNumIn != null){
		pageNum = pageNumIn/10;
		}
		++pageNum;
		int limtEnd = pageNum * pageLimit * 10;
		int limtStart = limtEnd - pageLimit * 10;
		if(limtStart <=0){
			limtStart = 0;	
		}else{
		pagination[0]= limtStart-1;
		}
		pagination[1]= limtEnd-1;
		return pagination;
	}
	
	
	private Set<Integer> getProductsBySearch(String sql, List<Object> inputs, String orderBy, String limit){
		String preSql = "SELECT DISTINCT PM.ID AS PRODUCT_ID";
		sql = preSql + sql;
		sql += orderBy;
		sql += limit;
		List<Integer> productList = jdbcTemplate.query(sql, inputs.toArray(), new RowMapper<Integer>(){
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer prodcutId = rs.getInt("PRODUCT_ID");
			return prodcutId;	
			}
		});
		return new HashSet<Integer>(productList);
	}
	
	private Integer getProductSearchCount(String sql, List<Object> inputs){
		String preSql = "SELECT COUNT(DISTINCT PM.ID) ";
		sql = preSql + sql;
		//sql += orderBy;
		Integer rowCount = jdbcTemplate.queryForObject(sql, inputs.toArray(), Integer.class);
		return rowCount;
	}
	
	private List<CategoryCountHelper> getCountByCategory(String sql, List<Object> inputs, String orderBy){
		String preSql = "SELECT CAT.ID as CAT_ID, CAT.CATEGORY_NAME AS CATEGORY_NAME, COUNT(PM.ID) as COUNT ";
		String postSql = " GROUP BY CAT.ID ";
		preSql = preSql + sql;
		postSql = preSql + postSql;
		postSql += orderBy;
		List<CategoryCountHelper> countByCategory = jdbcTemplate.query(postSql, inputs.toArray(), new RowMapper<CategoryCountHelper>(){
			@Override
			public CategoryCountHelper mapRow(ResultSet rs, int rowNum) throws SQLException {
				CategoryCountHelper countByCategory = new CategoryCountHelper();
				countByCategory.setCategoryId(rs.getInt("CAT_ID"));
				countByCategory.setCategoryName(rs.getString("CATEGORY_NAME"));
				countByCategory.setCount(rs.getInt("COUNT"));
			return countByCategory;	
			}
		});
		return countByCategory;
	}
	
	
	
	
	private List<ProductBase> getProductListByIds(String ids) {
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}
		
		String sql = "SELECT ID, PRODUCT_TITLE, SHORT_DESCRIPTION, PRODUCT_TYPE, PRODUCT_CODE, START_DATE, END_DATE,IMAGE_NAME,IS_ACTIVE"
				+ " FROM PRODUCT_MASTER WHERE ( IS_DELETED = 0 OR IS_DELETED IS NULL) AND ID IN (" + ids + ")";
		
		List<ProductBase> couponList = jdbcTemplate.query(sql, new RowMapper<ProductBase>() {
			@Override
			public ProductBase mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProductBase couponBase = new ProductBase();
				couponBase.setId(rs.getInt("ID"));
				couponBase.setTitle(rs.getString("PRODUCT_TITLE"));
				couponBase.setShortDescription(rs.getString("SHORT_DESCRIPTION"));
				couponBase.setType(rs.getString("PRODUCT_TYPE"));
				couponBase.setCode(rs.getString("PRODUCT_CODE"));
				couponBase.setStartDate(rs.getDate("START_DATE"));
				couponBase.setEndDate(rs.getDate("END_DATE"));
				couponBase.setImageName(rs.getString("IMAGE_NAME"));
				couponBase.setIsActive(rs.getInt("IS_ACTIVE"));
				return couponBase;
			}
		});
		return couponList;
	}
	
	
	public List<ProductCategoryRelationship> getProductCatRelationships() {
		
		String sql = "SELECT ID, PK_PRD_ID, PK_CAT_ID FROM CAT_PRODUCT_REL WHERE (IS_DELETED = 0 OR IS_DELETED IS NULL)";
		
		List<ProductCategoryRelationship> couCatRelData = jdbcTemplate.query(sql, new ProductCatParentRowMapper());
		couCatRelData = loadProductCatRelObjects(couCatRelData);
		return couCatRelData;
	}
	
	
	private List<ProductCategoryRelationship> loadProductCatRelObjects(List<ProductCategoryRelationship> productCatRelData) {
		if (productCatRelData != null && !productCatRelData.isEmpty()) {
			Set<Integer> productIds = new HashSet<Integer>();
			
			for (ProductCategoryRelationship iterator : productCatRelData) {
				productIds.add(iterator.getProductBase().getId());
			}
			
			if (!productIds.isEmpty()) {
				Map<Integer, ProductBase> map = executeProductQuery(new ArrayList<Integer>(productIds));
				
				for (ProductCategoryRelationship iterator : productCatRelData) {
					ProductBase productBase = map.get(iterator.getProductBase().getId());
					iterator.getProductBase().setId(productBase.getId());
					iterator.getProductBase().setTitle(productBase.getTitle());
					iterator.getProductBase().setShortDescription(productBase.getShortDescription());
					iterator.getProductBase().setType(productBase.getType());
					iterator.getProductBase().setCode(productBase.getCode());
					iterator.getProductBase().setStartDate(productBase.getStartDate());
					iterator.getProductBase().setEndDate(productBase.getEndDate());
					iterator.getProductBase().setImageName(productBase.getImageName());
					iterator.getProductBase().setIsActive(productBase.getIsActive());
				}
			}
		}
	return productCatRelData;
	}
	
	
	private Map<Integer, ProductBase> executeProductQuery(List<Integer> productIds) {
		List<ProductBase> finalList = new ArrayList<ProductBase>();
		
		if (productIds != null && !productIds.isEmpty()) {
			int count = 0;
			String ids = "";
			
			for (Integer iterator : productIds) {
				count++;
				ids += iterator + ",";
				
				if (count % 500 == 0) {
					List<ProductBase> empIdentityList = getProductListByIds(ids);
					finalList.addAll(empIdentityList);
					
					ids = "";
					count = 0;
				}
			}
			
			if (count > 0 || !ids.isEmpty()) {
				List<ProductBase> empIdentityList = getProductListByIds(ids);
				finalList.addAll(empIdentityList);
			}
		}
		
		Map<Integer, ProductBase> map = new HashMap<Integer, ProductBase>();
		
		if (!finalList.isEmpty()) {
			for (ProductBase iterator : finalList) {
				map.put(iterator.getId(), iterator);
			}
		}
		
		return map;
	}
	
	
	private class ProductCatParentRowMapper implements RowMapper<ProductCategoryRelationship> {
		@Override
		public ProductCategoryRelationship mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProductCategoryRelationship data = new ProductCategoryRelationship();
			data.setId(rs.getInt("ID"));
			
			data.setProductBase(new ProductBase());
			data.getProductBase().setId(rs.getInt("PK_PRD_ID"));
			
			data.setCategoryId(rs.getInt("PK_CAT_ID"));
			
			return data;
		}
	}
	
	
}
