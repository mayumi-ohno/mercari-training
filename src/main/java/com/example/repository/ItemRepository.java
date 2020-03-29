package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

/**
 * 商品情報を扱うリポジトリ.
 * 
 * @author mayumiono
 *
 */
@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private final static RowMapper<Item> ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setCondition(rs.getInt("condition"));
		item.setGrandChildCategoryId(rs.getInt("grand_chilid_category_id"));
		item.setGrandChildCategory(rs.getString("grand_chilid_category"));
		item.setChildCategoryId(rs.getInt("child_category_id"));
		item.setChildCategory(rs.getString("child_category"));
		item.setParentCategoryId(rs.getInt("parent_category_id"));
		item.setParentCategory(rs.getString("parent_category"));
		item.setBrand(rs.getString("brand"));
		item.setPrice(rs.getDouble("price"));
		item.setShipping(rs.getInt("shipping"));
		item.setDescription(rs.getString("description"));
		return item;
	};

	/**
	 * 指定の行から一定数の商品情報を、商品名昇順で取得する.
	 * 
	 * @param dataSize データ取得数
	 * @param index    読み込み開始行
	 * @return 商品情報
	 */
	public List<Item> findByIndex(Integer viewSize, Integer index) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A.id, A.name, A.condition, B.id AS grand_chilid_category_id, ");
		sql.append("B.name AS grand_chilid_category, C.id AS child_category_id, ");
		sql.append("C.name AS child_category, D.id AS parent_category_id, D.name AS parent_category, ");
		sql.append("A.brand, A.price, A.shipping, A.description ");
		sql.append("FROM items AS A	 LEFT OUTER JOIN category AS B ");
		sql.append("ON A.category=B.id ");
		sql.append("LEFT OUTER JOIN category AS C ON B.parent=C.id ");
		sql.append("LEFT OUTER JOIN category AS D ON C.parent=D.id ");
		sql.append("ORDER BY A.name ");
		sql.append("LIMIT :viewSize OFFSET :index;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("viewSize", viewSize).addValue("index", index);
		List<Item> itemList = template.query(sql.toString(), param, ROW_MAPPER);
		return itemList;
	}

	/**
	 * カテゴリ・商品名・ブランド名から商品情報を曖昧検索し、一定数のデータを商品名昇順で取得する.
	 * 
	 * @param item     検索情報
	 * @param viewSize データ取得数
	 * @param index    読み込み開始行
	 * @return 商品情報
	 */
	public List<Item> fuzzySearch(Item item, Integer viewSize, Integer index) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A.id, A.name, A.condition, B.id AS grand_chilid_category_id, ");
		sql.append("B.name AS grand_chilid_category, C.id AS child_category_id, ");
		sql.append("C.name AS child_category, D.id AS parent_category_id, D.name AS parent_category, ");
		sql.append("A.brand, A.price, A.shipping, A.description ");
		sql.append("FROM items AS A	 LEFT OUTER JOIN category AS B ");
		sql.append("ON A.category=B.id ");
		sql.append("LEFT OUTER JOIN category AS C ON B.parent=C.id ");
		sql.append("LEFT OUTER JOIN category AS D ON C.parent=D.id ");
		sql.append("WHERE A.id IS NOT NULL ");
		if (item.getName() != null) {
			sql.append("AND A.name LIKE :name ");
		}
		if (item.getBrand() != null) {
			sql.append("AND A.brand LIKE :brand ");
		}
		if (item.getParentCategoryId() != null) {
			sql.append("AND D.id =:parentCategoryId ");
		}
		if (item.getChildCategoryId() != null) {
			sql.append("AND C.id =:childCategoryId ");
		}
		if (item.getGrandChildCategoryId() != null) {
			sql.append("AND B.id =:grandChildCategoryId ");
		}
		sql.append("ORDER BY A.name ");
		sql.append("LIMIT :viewSize OFFSET :index;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentCategoryId", item.getParentCategoryId())
				.addValue("childCategoryId", item.getChildCategoryId())
				.addValue("grandChildCategoryId", item.getGrandChildCategoryId())
				.addValue("name", "%" + item.getName() + "%").addValue("brand", "%" + item.getBrand() + "%")
				.addValue("viewSize", viewSize).addValue("index", index);
		List<Item> itemList = template.query(sql.toString(), param, ROW_MAPPER);
		return itemList;
	}

	/**
	 * 全商品数を取得する.
	 * 
	 * @return 全商品数
	 */
	public Integer getAllDataSize() {
		String sql = "SELECT COUNT(id) FROM items;";
		SqlParameterSource param = new MapSqlParameterSource();
		Integer dataSize = template.queryForObject(sql.toString(), param, Integer.class);
		return dataSize;
	}

	/**
	 * 曖昧検索時の全商品数を取得する.
	 * 
	 * @param item 検索商品情報
	 * @return 該当商品数
	 */
	public Integer getDataSizeWhenSearch(Item item) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(A.id) ");
		sql.append("FROM items AS A	 LEFT OUTER JOIN category AS B ");
		sql.append("ON A.category=B.id ");
		sql.append("LEFT OUTER JOIN category AS C ON B.parent=C.id ");
		sql.append("LEFT OUTER JOIN category AS D ON C.parent=D.id ");
		sql.append("WHERE A.id IS NOT NULL ");
		if (item.getName() != null) {
			sql.append("AND A.name LIKE :name ");
		}
		if (item.getBrand() != null) {
			sql.append("AND A.brand LIKE :brand ");
		}
		if (item.getParentCategoryId() != null) {
			sql.append("AND D.id =:parentCategoryId ");
		}
		if (item.getChildCategoryId() != null) {
			sql.append("AND C.id =:childCategoryId ");
		}
		if (item.getGrandChildCategoryId() != null) {
			sql.append("AND B.id =:grandChildCategoryId ");
		}
		sql.append(";");
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentCategoryId", item.getParentCategoryId())
				.addValue("childCategoryId", item.getChildCategoryId())
				.addValue("grandChildCategoryId", item.getGrandChildCategoryId())
				.addValue("name", "%" + item.getName() + "%").addValue("brand", "%" + item.getBrand() + "%");

		Integer dataSize = template.queryForObject(sql.toString(), param, Integer.class);
		return dataSize;
	}

}
