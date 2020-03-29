package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;

/**
 * カテゴリ情報を扱うリポジトリ.
 * 
 * @author mayumiono
 *
 */
@Repository
public class CategoryRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private final static RowMapper<Category> ROW_MAPPER = (rs, i) -> {
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setParent(rs.getInt("parent"));
		category.setName(rs.getString("name"));
		category.setName_all(rs.getString("name_all"));
		return category;
	};

	/**
	 * カテゴリ情報一覧を名前昇順で取得する.
	 * 
	 * @return カテゴリ情報一覧
	 */
	public List<Category> findAll() {
		String sql = "SELECT id, parent, name, name_all FROM category ORDER BY name;";
		List<Category> categoryList = template.query(sql, ROW_MAPPER);
		return categoryList;
	}

}
