package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.domain.LoginUser;
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
		return new SearchForm();
	}

	/**
	 * 商品一覧を表示する.
	 * 
	 * @return 商品一覧ページ
	 */
	@RequestMapping("")
	public String index(SearchForm form, Integer page, Model model, @AuthenticationPrincipal LoginUser loginUser) {

		session.setAttribute("loginUser", loginUser.getUser()); // ログイン情報を格納

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
		Integer totalPages = null;
		if (page == null) {
			totalPages = showListService.calcPageNumber(VIEW_SIZE, form);
			session.setAttribute("totalPages", totalPages);
		}

		// ページ数の指定が無い場合は1ページ目を表示させる
		if (page == null) {
			page = 1;
		}

		totalPages = (Integer) session.getAttribute("totalPages");
		if (!(1 <= page && page <= totalPages)) {
			model.addAttribute("pagerError", "error:enter numbers range 1-" + totalPages);
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