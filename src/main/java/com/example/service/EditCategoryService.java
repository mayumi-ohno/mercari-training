package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Category;
import com.example.form.EditCategoryForm;
import com.example.repository.CategoryRepository;

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
}
