package com.example.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.form.EditItemDetailForm;
import com.example.repository.ItemRepository;

/**
 * 商品情報編集処理をするサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class EditDetailService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品情報を編集する.
	 * 
	 * @param form 商品情報
	 */
	public void edit(EditItemDetailForm form) {
		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		item.setId(Integer.parseInt(form.getId()));
		item.setGrandChildCategoryId(Integer.parseInt(form.getGrandChildCategoryId()));
		item.setCondition(Integer.parseInt(form.getCondition()));
		item.setPrice(Double.parseDouble(form.getPrice()));
		itemRepository.update(item);
	}

}
