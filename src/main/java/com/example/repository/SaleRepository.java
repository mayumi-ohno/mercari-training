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
	 * セール情報を追加する.
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
