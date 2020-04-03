package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.EditCategoryForm;
import com.example.repository.CategoryRepository;
import com.example.repository.ItemRepository;

/**
 * カテゴリ編集を行うサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class EditCategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 既存カテゴリのカテゴリ名更新をする.
	 * 
	 * @param form 編集情報
	 * @return 成功：true, 失敗：false
	 */
	public boolean updateExistingCategory(EditCategoryForm form) {
		boolean existParentCategoryId = !"".equals(form.getParentCategoryId());
		boolean existChildCategoryId = !"".equals(form.getChildCategoryId());
		boolean existGrandChildCategoryId = !"".equals(form.getGrandChildCategoryId());

		Category category = new Category();

		if (existGrandChildCategoryId) {
			category.setId(Integer.parseInt(form.getGrandChildCategoryId()));
			category.setParent(Integer.parseInt(form.getChildCategoryId()));
			category.setName(form.getGrandChildCategoryName());
			String nameAll = form.getParentCategoryName() + "/" + form.getChildCategoryName() + "/"
					+ form.getGrandChildCategoryName();
			category.setNameAll(nameAll);
		} else if (existChildCategoryId) {
			category.setId(Integer.parseInt(form.getChildCategoryId()));
			category.setParent(Integer.parseInt(form.getParentCategoryId()));
			category.setName(form.getChildCategoryName());
		} else if (existParentCategoryId) {
			category.setId(Integer.parseInt(form.getParentCategoryId()));
			category.setName(form.getParentCategoryName());
		}

		if (categoryRepository.searchId(category) != null) {
			return false;
		}

		categoryRepository.updateCategory(category);
		return true;
	}

	/**
	 * 新規カテゴリを追加する.
	 * 
	 * @param form 編集情報
	 * @return 追加成功：true, 失敗：false
	 */
	public boolean addNewCategory(EditCategoryForm form) {
		boolean existParentCategoryId = !"".equals(form.getParentCategoryId()) && form.getParentCategoryId() != null;
		boolean existChildCategoryId = !"".equals(form.getChildCategoryId()) && form.getChildCategoryId() != null;

		Category category = new Category();

		if (existChildCategoryId) {
			category.setParent(Integer.parseInt(form.getChildCategoryId()));
			category.setName(form.getGrandChildCategoryName());
			String nameAll = form.getParentCategoryName() + "/" + form.getChildCategoryName() + "/"
					+ form.getGrandChildCategoryName();
			category.setNameAll(nameAll);
		} else if (existParentCategoryId) {
			category.setParent(Integer.parseInt(form.getParentCategoryId()));
			category.setName(form.getChildCategoryName());
		} else if (!existParentCategoryId && !existChildCategoryId) {
			category.setName(form.getParentCategoryName());
		}

		if (categoryRepository.searchId(category) != null) {
			return false;
		}

		categoryRepository.insertCategory(category);
		return true;
	}

	/**
	 * 既存カテゴリを削除する.
	 * 
	 * @param form カテゴリ
	 * @return 該当カテゴリに属する商品数
	 */
	public Integer deleteCategory(EditCategoryForm form) {
		Integer itemsBelongToThisCategory = 0;
		Item item = copyPropertiesFromFormToItem(form);
		itemsBelongToThisCategory = itemRepository.getDataSizeWhenSearch(item);
		if (itemsBelongToThisCategory != 0) {
			return itemsBelongToThisCategory;
		}

		boolean existParentCategoryId = !"".equals(form.getParentCategoryId()) && form.getParentCategoryId() != null;
		boolean existChildCategoryId = !"".equals(form.getChildCategoryId()) && form.getChildCategoryId() != null;
		boolean existGrandChildCategoryId = !"".equals(form.getGrandChildCategoryId())
				&& form.getGrandChildCategoryId() != null;
		Integer categoryId = null;

		if (existGrandChildCategoryId) {
			categoryId = Integer.parseInt(form.getGrandChildCategoryId());
		} else if (existChildCategoryId) {
			categoryId = Integer.parseInt(form.getChildCategoryId());
		} else if (existParentCategoryId) {
			categoryId = Integer.parseInt(form.getParentCategoryId());
		}

		categoryRepository.deleteById(categoryId);

		return 0;
	}

	/**
	 * フォームオブジェクトからドメインオブジェクトへ値コピーする.
	 * 
	 * @param form フォーム
	 * @return ドメイン
	 */
	public Item copyPropertiesFromFormToItem(EditCategoryForm form) {
		Item item = new Item();
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
		return item;
	}
}
