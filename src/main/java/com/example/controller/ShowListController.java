package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SearchForm;
import com.example.service.CategoryService;
import com.example.service.ShowListService;

/**
 * 商品一覧表示のコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/items")
public class ShowListController {

	/** 1ページに表示する商品数 */
	private final static Integer VIEW_SIZE = 30;

	@Autowired
	private ShowListService showListService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public SearchForm setUpForm() {
		return  new SearchForm();
	}

	/**
	 * 商品一覧を表示する.
	 * 
	 * @return 商品一覧ページ
	 */
	@RequestMapping("")
	public String index(SearchForm form, Integer page, Model model) {

		// カテゴリリスト(検索用)作成
		if (session.getAttribute("parentCategoryList") == null) {
			List<Category> categoryList = categoryService.getAllCategory();
			List<Category> parentCategoryList = categoryService.createParentCategoryList(categoryList);
			session.setAttribute("parentCategoryList", parentCategoryList);
			List<Category> childCategoryList = categoryService.createChildCategoryList(categoryList);
			session.setAttribute("childCategoryList", childCategoryList);
			List<Category> grandChildCategoryList = categoryService.createGrandChildCategoryList(categoryList);
			session.setAttribute("grandChildCategoryList", grandChildCategoryList);
		}

		// 総ページ数算出
		if (page == null) {
			int totalPages = showListService.calcPageNumber(VIEW_SIZE, form);
			session.setAttribute("totalPages", totalPages);
		}

		// ページ数の指定が無い場合は1ページ目を表示させる
		if (page == null) {
			page = 1;
		}
		List<Item> items = showListService.searchItems(form, VIEW_SIZE, page);
		model.addAttribute("items", items);

		// 現在のページ番号・検索条件を保存しておく
		session.setAttribute("page", page);
		session.setAttribute("searchValue", form);

		return "list";
	}

}