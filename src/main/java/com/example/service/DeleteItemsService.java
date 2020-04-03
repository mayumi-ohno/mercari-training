package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.ItemRepository;

/**
 * 商品削除をするサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class DeleteItemsService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品削除.
	 * 
	 * @param itemIdList 削除対象の商品ID
	 */
	public void deleteItems(Integer[] itemIdList) {
		StringBuilder itemIdListForSql = new StringBuilder();
		for (Integer itemId : itemIdList) {
			itemIdListForSql.append(itemId + ",");
		}
		itemIdListForSql.setLength(itemIdListForSql.length() - 1);
		itemRepository.delete(itemIdListForSql);
	}

}
