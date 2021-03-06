package com.example.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Sale;

/**
 * 商品情報を扱うリポジトリ.
 * 
 * @author mayumiono
 *
 */
@Repository
public class ItemRepository {

	private static final Logger logger = LoggerFactory.getLogger(ItemRepository.class);

	@Autowired
	private NamedParameterJdbcTemplate template;

	/** 商品情報を生成するローマッパー */
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
		item.setImage(rs.getString("image"));
		Sale sale = new Sale();
		sale.setStart(rs.getDate("start"));
		sale.setEnd(rs.getDate("period"));
		sale.setDiscountRate(rs.getInt("discount_rate"));
		item.setSale(sale);
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
		sql.append(commonPartOfSql());
		sql.append("ORDER BY A.name ");
		sql.append("LIMIT :viewSize OFFSET :index;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("viewSize", viewSize).addValue("index", index);
		List<Item> itemList = template.query(sql.toString(), param, ROW_MAPPER);
		return itemList;
	}

	/**
	 * 全商品情報を引数のIDから指定件数分、商品名昇順で取得する.
	 * 
	 * @param limit  1回当のデータ数
	 * @param offset データ読み込み開始行
	 * @return 商品情報
	 */
	public List<Item> findAllLimited(Integer limit, Integer offset) {
		StringBuilder sql = new StringBuilder();
		sql.append(commonPartOfSql());
		sql.append("ORDER BY name ");
		sql.append("LIMIT :limit OFFSET :offset;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("limit", limit).addValue("offset", offset);
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
		sql.append(commonPartOfSql());
		sql.append("WHERE A.id IS NOT NULL ");
		sql.append(commonPartOfSqlForFuzzySearch(item));
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
		sql.append("LEFT OUTER JOIN brand AS E ON A.brand=E.id ");
		sql.append("WHERE A.id IS NOT NULL ");
		sql.append(commonPartOfSqlForFuzzySearch(item));
		sql.append(";");
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentCategoryId", item.getParentCategoryId())
				.addValue("childCategoryId", item.getChildCategoryId())
				.addValue("grandChildCategoryId", item.getGrandChildCategoryId())
				.addValue("name", "%" + item.getName() + "%").addValue("brand", "%" + item.getBrand() + "%");

		Integer dataSize = template.queryForObject(sql.toString(), param, Integer.class);
		return dataSize;
	}

	/**
	 * 商品IDで商品情報を検索・取得する.
	 * 
	 * @param itemId 商品ID
	 * @return 商品情報
	 */
	public Item findByItemId(Integer itemId) {
		StringBuilder sql = new StringBuilder();
		sql.append(commonPartOfSql());
		sql.append("WHERE A.id=:itemId;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);
		Item item;
		try {
			item = template.queryForObject(sql.toString(), param, ROW_MAPPER);
		} catch (Exception e) {
			return null;
		}
		return item;
	}

	/**
	 * 商品情報を更新する.
	 * 
	 * @param item 商品情報
	 */
	public void update(Item item) {
		StringBuilder sql = new StringBuilder();
		sql.append("WITH brand_name AS (");
		sql.append("UPDATE items SET name=:name, price=:price, condition=:condition, ");
		sql.append("category=:grandChildCategoryId, description=:description, image=:image ");
		sql.append("WHERE id=:id) ");
		sql.append("INSERT INTO brand (name) SELECT :brand ");
		sql.append("WHERE NOT EXISTS (SELECT id FROM brand WHERE name=:brand);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql.toString(), param);

		sql = new StringBuilder();
		sql.append("UPDATE items SET brand=(SELECT id FROM brand WHERE name=:brand) ");
		sql.append("WHERE id=:id;");
		template.update(sql.toString(), param);
		logger.info("【既存商品更新】id:" + item.getId() + ", name:" + item.getName() + ", price:" + item.getPrice());
	}

	/**
	 * 商品情報を追加する.
	 * 
	 * @param item 商品情報
	 */
	public void insert(Item item) {
		StringBuilder sql = new StringBuilder();
		sql.append("WITH brand_name AS (INSERT INTO items ");
		if (item.getImage() == null || "".equals(item.getImage())) {
			sql.append("(id,name,condition,category,price,description) ");
			sql.append("VALUES((SELECT MAX(id)+1 FROM items),:name,:condition,:grandChildCategoryId, ");
			sql.append(":price,:description) ");
		} else {
			sql.append("(id,name,condition,category,price,description,image) ");
			sql.append("VALUES((SELECT MAX(id)+1 FROM items),:name,:condition,:grandChildCategoryId,");
			sql.append(":price,:description,:image) ");
		}
		sql.append("RETURNING id, brand) ");
		sql.append(", insert_brand AS (INSERT INTO brand (name) SELECT brand FROM brand_name ");
		sql.append("WHERE NOT EXISTS (SELECT id FROM brand WHERE name=:brand)) ");
		sql.append("UPDATE items SET brand=(SELECT id FROM brand WHERE name=:brand) ");
		sql.append("WHERE id=(SELECT id FROM brand WHERE name=:brand);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql.toString(), param);
		logger.info("【新規商品追加】 name:" + item.getName() + ", price:" + item.getPrice());
	}

	/**
	 * 商品を削除する.
	 * 
	 * @param itemId 商品ID
	 */
	public void delete(StringBuilder itemIdList) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM items WHERE id IN (");
		sql.append(itemIdList);
		sql.append(");");
		SqlParameterSource param = new MapSqlParameterSource();
		template.update(sql.toString(), param);
		logger.info("【既存商品削除】id:" + itemIdList);
	}

	/**
	 * SQL文の共通部分を生成する.
	 * 
	 * @return SQL文（共通部分）
	 */
	public StringBuilder commonPartOfSql() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A.id, A.name, A.condition, B.id AS grand_chilid_category_id, ");
		sql.append("B.name AS grand_chilid_category, C.id AS child_category_id, ");
		sql.append("C.name AS child_category, D.id AS parent_category_id, D.name AS parent_category, ");
		sql.append("E.name AS brand, A.price, A.shipping, A.description, A.image, ");
		sql.append("sale.start, sale.period, sale.discount_rate ");
		sql.append("FROM items AS A	 LEFT OUTER JOIN category AS B ");
		sql.append("ON A.category=B.id ");
		sql.append("LEFT OUTER JOIN category AS C ON B.parent=C.id ");
		sql.append("LEFT OUTER JOIN category AS D ON C.parent=D.id ");
		sql.append("LEFT OUTER JOIN brand AS E ON A.brand=E.id ");
		sql.append("LEFT OUTER JOIN sale ON A.id=sale.item_id ");
		return sql;
	}

	/**
	 * 曖昧検索用SQLの共通部分を生成する.
	 * 
	 * @param item 検索条件
	 * @return SQL文（共通部分）
	 */
	public StringBuilder commonPartOfSqlForFuzzySearch(Item item) {
		StringBuilder sql = new StringBuilder();
		if (item.getName() != null) {
			sql.append("AND A.name ILIKE :name ");
		}
		if (item.getBrand() != null && !"".equals(item.getBrand())) {
			sql.append("AND E.name ILIKE :brand ");
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
		return sql;
	}
}
