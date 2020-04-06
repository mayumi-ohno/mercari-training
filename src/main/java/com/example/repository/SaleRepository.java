package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Sale;

/**
 * セール情報を扱うリポジトリ.
 * 
 * @author mayumiono
 *
 */
@Repository
public class SaleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 商品ID指定でセール情報を追加する.
	 * 
	 * @param saleList セール情報
	 */
	public void insert(List<Sale> saleList) {
		StringBuilder values = new StringBuilder();
		StringBuilder itemIdString = new StringBuilder();
		saleList.forEach(sale -> {
			values.append("(" + sale.getItemId() + ",CURRENT_TIMESTAMP,:start,:end,:discountRate),");
			itemIdString.append(sale.getItemId() + ",");
		});
		values.setLength(values.length() - 1);
		itemIdString.setLength(itemIdString.length() - 1);

		StringBuilder sql = new StringBuilder();
		sql.append("WITH new_sale AS( ");
		sql.append("INSERT INTO sale (item_id,setting,start,period,discount_rate) VALUES ");
		sql.append(values);
		sql.append(") DELETE FROM sale WHERE setting<CURRENT_TIMESTAMP AND item_id IN (");
		sql.append(itemIdString);
		sql.append(");");
		SqlParameterSource param = new BeanPropertySqlParameterSource(saleList.get(0));
		template.update(sql.toString(), param);
	}

	/**
	 * 検索条件に合致する商品をセールにする .
	 * 
	 * @param sale セール情報
	 */
	public void insertWhenSearching(Sale sale) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO sale (item_id,setting,start,period,discount_rate) ");
		sql.append("SELECT A.id,CURRENT_TIMESTAMP,:start,:end,:discountRate ");
		sql.append("FROM items AS A	 LEFT OUTER JOIN category AS B ");
		sql.append("ON A.category=B.id ");
		sql.append("LEFT OUTER JOIN category AS C ON B.parent=C.id ");
		sql.append("LEFT OUTER JOIN category AS D ON C.parent=D.id ");
		sql.append("LEFT OUTER JOIN brand AS E ON A.brand=E.id ");
		sql.append("WHERE A.id IS NOT NULL ");
		if (sale.getName() != null && !"".equals(sale.getName())) {
			sql.append("AND A.name ILIKE :name ");
		}
		if (sale.getBrand() != null && !"".equals(sale.getBrand())) {
			sql.append("AND E.name ILIKE :brand ");
		}
		if (sale.getParentCategoryId() != null) {
			sql.append("AND D.id =:parentCategoryId ");
		}
		if (sale.getChildCategoryId() != null) {
			sql.append("AND C.id =:childCategoryId ");
		}
		if (sale.getGrandChildCategoryId() != null) {
			sql.append("AND B.id =:grandChildCategoryId ");
		}
		sql.append(";");
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentCategoryId", sale.getParentCategoryId())
				.addValue("childCategoryId", sale.getChildCategoryId())
				.addValue("grandChildCategoryId", sale.getGrandChildCategoryId())
				.addValue("name", "%" + sale.getName() + "%").addValue("brand", "%" + sale.getBrand() + "%")
				.addValue("start", sale.getStart()).addValue("end", sale.getEnd())
				.addValue("discountRate", sale.getDiscountRate());
		template.update(sql.toString(), param);
	}

	/**
	 * セール情報を削除する.
	 * 
	 * @param itemIdList 商品ID
	 */
	public void delete(Integer[] itemIdList) {
		StringBuilder itemIdString = new StringBuilder();
		for (Integer itemId : itemIdList) {
			itemIdString.append(itemId + ",");
		}
		itemIdString.setLength(itemIdString.length() - 1);

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM sale WHERE item_id IN (");
		sql.append(itemIdString);
		sql.append(");");
		SqlParameterSource param = new MapSqlParameterSource();
		template.update(sql.toString(), param);
	}

}
