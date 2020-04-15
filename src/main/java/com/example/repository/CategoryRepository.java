package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
		category.setNameAll(rs.getString("name_all"));
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

	/**
	 * 既存カテゴリのカテゴリ名を更新する.
	 * 
	 * @param category カテゴリ情報
	 */
	public void updateCategory(Category category) {
		StringBuilder sql = new StringBuilder();
		//孫カテゴリ編集の場合
		if (category.getNameAll() != null && category.getParent() != null) {
			sql.append("UPDATE category SET parent=:parent, name=:name, name_all=:nameAll WHERE id=:id;");
		}
		//子カテゴリ編集の場合
		if (category.getNameAll() == null && category.getParent() != null) {
			sql.append("WITH update_id AS ( ");
			sql.append("UPDATE category SET name=:name WHERE id=:id RETURNING id) ");
			sql.append("UPDATE category SET name_all=SPLIT_PART(name_all, '/',1)||'/'||:name||'/'||name ");
			sql.append(" WHERE parent IN (SELECT id FROM update_id);");
		}
		//親カテゴリ編集の場合
		if (category.getNameAll() == null && category.getParent() == null) {
			sql.append("WITH update_id AS ( ");
			sql.append("UPDATE category SET name=:name WHERE id=:id RETURNING id) ");
			sql.append("UPDATE category SET name_all=:name||'/'||SPLIT_PART(name_all, '/',2)||'/'||name ");
			sql.append("WHERE parent IN (SELECT id FROM category WHERE parent IN(SELECT id FROM category WHERE id=:id) );");
		}
		SqlParameterSource param = new BeanPropertySqlParameterSource(category);
		template.update(sql.toString(), param);
	}

	/**
	 * 新規カテゴリを追加する.
	 * 
	 * @param category カテゴリ情報
	 */
	public void insertCategory(Category category) {
		String sql = "INSERT INTO category (parent,name,name_all) VALUES(:parent,:name,:nameAll);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(category);
		template.update(sql, param);
	}

	/**
	 * カテゴリIDを検索する.
	 * 
	 * @param category カテゴリ情報
	 * @return カテゴリID(該当なしの場合null)
	 */
	public Integer searchId(Category category) {
		StringBuilder sql = new StringBuilder("SELECT id FROM category WHERE name=:name ");
		if (category.getParent() == null) {
			sql.append("AND parent IS NULL ");
		} else {
			sql.append("AND parent=:parent ");
		}
		if (category.getNameAll() == null) {
			sql.append("AND name_all IS NULL ");
		} else {
			sql.append("AND name_all=:nameAll ");
		}
		sql.append(";");
		SqlParameterSource param = new BeanPropertySqlParameterSource(category);
		try {
			Integer categoryId = template.queryForObject(sql.toString(), param, Integer.class);
			return categoryId;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 引数のカテゴリIDに該当する既存カテゴリを削除する.
	 * 
	 * @param categoryId カテゴリID
	 */
	public void deleteById(Integer categoryId) {
		String sql = "DELETE FROM  category WHERE id=:categoryId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId);
		template.update(sql, param);
	}
}
