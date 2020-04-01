package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Category;
import com.example.repository.CategoryRepository;

/**
 * カテゴリ情報を処理するサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * 全カテゴリ情報を取得する.
	 * 
	 * @return mayumiono
	 */
	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	/**
	 * 親カテゴリリストを作成する.
	 * 
	 * @param categoryList 全カテゴリリスト
	 * @return 親カテゴリリスト
	 */
	public List<Category> createParentCategoryList(List<Category> categoryList) {
		List<Category> parentCategoryList = new ArrayList<>();
		for (Category category : categoryList) {
			if (category.getParent() == 0 && category.getNameAll() == null) {
				parentCategoryList.add(category);
			}
		}
		return parentCategoryList;
	}

	/**
	 * 子カテゴリリストを作成する.
	 * 
	 * @param categoryList 全カテゴリリスト
	 * @return 子カテゴリリスト
	 */
	public List<Category> createChildCategoryList(List<Category> categoryList) {
		List<Category> childCategoryList = new ArrayList<>();
		for (Category category : categoryList) {
			if (category.getParent() != 0 && category.getNameAll() == null) {
				childCategoryList.add(category);
			}
		}
		return childCategoryList;
	}

	/**
	 * 孫カテゴリリストを作成する.
	 * 
	 * @param categoryList 全カテゴリリスト
	 * @return 孫カテゴリリスト
	 */
	public List<Category> createGrandChildCategoryList(List<Category> categoryList) {
		List<Category> garandChildCategoryList = new ArrayList<>();
		for (Category category : categoryList) {
			if (category.getParent() != 0 && category.getNameAll() != null) {
				garandChildCategoryList.add(category);
			}
		}
		return garandChildCategoryList;
	}
}
