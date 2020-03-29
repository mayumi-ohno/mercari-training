package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.form.SearchForm;
import com.example.repository.ItemRepository;

/**
 * 商品一覧表示処理を行うサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class ShowListService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品の検索をする.
	 * 
	 * @param form     検索情報
	 * @param viewSize 表示する商品数
	 * @param page     ページ数
	 * @return 商品一覧
	 */
	public List<Item> searchItems(SearchForm form, Integer viewSize, Integer page) {
		Integer indexOfTop = (page - 1) * viewSize;
		boolean doingFuzzySearch = form.existValues();
		if (doingFuzzySearch) {
			Item item = copyPropertiesFromFormToItem(form);
			return itemRepository.fuzzySearch(item, viewSize, indexOfTop);
		} else {
			return itemRepository.findByIndex(viewSize, indexOfTop);
		}
	}

	/**
	 * 総ページ数を算出する
	 * 
	 * @param viewSize 1ページ当に表示する商品数
	 * @param form     検索条件
	 * @return 総ページ数
	 */
	public Integer calcPageNumber(int viewSize, SearchForm form) {
		boolean doingFuzzySearch = form.existValues();
		int allItems = 0;
		if (doingFuzzySearch) {
			Item item = copyPropertiesFromFormToItem(form);
			allItems = itemRepository.getDataSizeWhenSearch(item);
		} else {
			allItems = itemRepository.getAllDataSize();
		}

		int totalPages = 0;
		if (allItems % viewSize == 0) {
			totalPages = allItems / viewSize;
		} else {
			totalPages = allItems / viewSize + 1;
		}
		return totalPages;
	}

	/**
	 * フォームオブジェクトからドメインオブジェクトへ値コピーする.
	 * 
	 * @param form フォーム
	 * @return ドメイン
	 */
	public Item copyPropertiesFromFormToItem(SearchForm form) {
		Item item = new Item();
		item.setName(form.getName());
		try {
			item.setParentCategoryId(Integer.parseInt(form.getParentCategoryId()));
		} catch (NumberFormatException e) {
			item.setParentCategoryId(null);
		}
		try {
			item.setChildCategoryId(Integer.parseInt(form.getChildCategoryId()));
		} catch (NumberFormatException e) {
			item.setChildCategoryId(null);
		}
		try {
			item.setGrandChildCategoryId(Integer.parseInt(form.getGrandChildCategoryId()));
		} catch (NumberFormatException e) {
			item.setGrandChildCategoryId(null);
		}
		item.setBrand(form.getBrand());
		return item;
	}

}
