package com.example.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.form.AddItemForm;
import com.example.repository.ItemRepository;

/**
 * 商品追加処理をするサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class AddItemService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品を追加する.
	 * 
	 * @param form 商品情報
	 */
	public void addItem(AddItemForm form) {
		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		item.setGrandChildCategoryId(Integer.parseInt(form.getGrandChildCategoryId()));
		item.setCondition(Integer.parseInt(form.getCondition()));
		item.setPrice(Double.parseDouble(form.getPrice()));
		itemRepository.insert(item);
	}

}
