package com.hertz.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hertz.entity.CatCatRel;
import com.hertz.entity.Category;


/**
 * 
 *  @author Subba Rao Ch
 */
@Component
public class CategoryDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate = null;
	
	public Integer getCategoryCount(Integer categoryId) {
		String noOfRecQuery = "SELECT count(*) AS RECORDS FROM categories";
		Integer noOfRec = jdbcTemplate.queryForObject(noOfRecQuery, Integer.class);
		System.out.println("Helo===>"+jdbcTemplate +"num of ecords===>"+noOfRec);
	return 	noOfRec;
	}
	
	
	private List<Category> getCategoryListByIds(String ids) {
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}
		
		String sql = "SELECT ID, CATEGORY_IMAGE, CATEGORY_NAME, CATEGORY_DESC, SORT_ORDER, DATE_ADDED, LAST_MODIFIED FROM categories WHERE ( IS_DELETED = 0 OR IS_DELETED IS NULL) AND ID IN (" + ids + ")";
		
		List<Category> categoryList = jdbcTemplate.query(sql, new RowMapper<Category>() {
			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category category = new Category();
				category.setId(rs.getInt("ID"));
				category.setCategoryName(rs.getString("CATEGORY_NAME"));
				category.setCategoryDesc(rs.getString("CATEGORY_DESC"));
				category.setCategoryImage(rs.getString("CATEGORY_IMAGE"));
				category.setSortOrder(rs.getInt("SORT_ORDER"));
				category.setDateAdded(rs.getDate("DATE_ADDED"));
				category.setLastModified(rs.getDate("LAST_MODIFIED"));
				return category;
			}
		});
		return categoryList;
	}
	
	
	public List<CatCatRel> getParentRelationships() {
		
		String sql = "SELECT ID, PK_CAT_ID, CAT_PARENT_ID FROM cat_rel_cat_parent WHERE (IS_DELETED = 0 OR IS_DELETED IS NULL)";
		
		List<CatCatRel> supRelData = jdbcTemplate.query(sql, new CatParentRowMapper());
		supRelData = loadSupEmpIdenitityObjects(supRelData);
		return supRelData;
	}
	
	
	private List<CatCatRel> loadSupEmpIdenitityObjects(List<CatCatRel> catRelData) {
		if (catRelData != null && !catRelData.isEmpty()) {
			Set<Integer> catIds = new HashSet<Integer>();
			
			for (CatCatRel iterator : catRelData) {
				catIds.add(iterator.getCat().getId());
				catIds.add(iterator.getCatParent().getId());
			}
			
			if (!catIds.isEmpty()) {
				Map<Integer, Category> map = executeEmployeeIdentityQuery(new ArrayList<Integer>(catIds));
				
				for (CatCatRel iterator : catRelData) {
					Category category = map.get(iterator.getCat().getId());
					iterator.getCat().setCategoryName(category.getCategoryName());
					iterator.getCat().setCategoryDesc(category.getCategoryDesc());
					iterator.getCat().setCategoryImage(category.getCategoryImage());
					iterator.getCat().setDateAdded(category.getDateAdded());
					iterator.getCat().setLastModified(category.getLastModified());
					iterator.getCat().setSortOrder(category.getSortOrder());
					
					Category catParent = map.get(iterator.getCatParent().getId());
					iterator.getCatParent().setCategoryName(catParent.getCategoryName());
					iterator.getCatParent().setCategoryDesc(catParent.getCategoryDesc());
					iterator.getCatParent().setCategoryImage(catParent.getCategoryImage());
					iterator.getCatParent().setDateAdded(catParent.getDateAdded());
					iterator.getCatParent().setLastModified(catParent.getLastModified());
					iterator.getCatParent().setSortOrder(catParent.getSortOrder());
				}
			}
		}
	return catRelData;
	}
	
	
	private Map<Integer, Category> executeEmployeeIdentityQuery(List<Integer> catIds) {
		List<Category> finalList = new ArrayList<Category>();
		
		if (catIds != null && !catIds.isEmpty()) {
			int count = 0;
			String ids = "";
			
			for (Integer iterator : catIds) {
				count++;
				ids += iterator + ",";
				
				if (count % 500 == 0) {
					List<Category> empIdentityList = getCategoryListByIds(ids);
					finalList.addAll(empIdentityList);
					
					ids = "";
					count = 0;
				}
			}
			
			if (count > 0 || !ids.isEmpty()) {
				List<Category> empIdentityList = getCategoryListByIds(ids);
				finalList.addAll(empIdentityList);
			}
		}
		
		Map<Integer, Category> map = new HashMap<Integer, Category>();
		
		if (!finalList.isEmpty()) {
			for (Category iterator : finalList) {
				map.put(iterator.getId(), iterator);
			}
		}
		
		return map;
	}
	
	
	private class CatParentRowMapper implements RowMapper<CatCatRel> {
		@Override
		public CatCatRel mapRow(ResultSet rs, int rowNum) throws SQLException {
			CatCatRel data = new CatCatRel();
			data.setId(rs.getInt("ID"));
			
			data.setCat(new Category());
			data.getCat().setId(rs.getInt("PK_CAT_ID"));
			
			data.setCatParent(new Category());
			data.getCatParent().setId(rs.getInt("CAT_PARENT_ID"));
			
			return data;
		}
	}
}
