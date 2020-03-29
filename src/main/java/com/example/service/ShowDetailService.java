package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品詳細表示をするサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class ShowDetailService {

	@Autowired
	private ItemRepository itemRepository;

	public Item showDetail(Integer itemId) {
		return itemRepository.findByItemId(itemId);
	}

}
