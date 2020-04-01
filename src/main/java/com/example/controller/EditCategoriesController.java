package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.Category;
import com.example.form.EditCategoryForm;
import com.example.service.CategoryService;
import com.example.service.EditCategoryService;

/**
 * カテゴリ編集を行うコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/category")
public class EditCategoriesController {

	@Autowired
	private EditCategoryService editCategoryService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public EditCategoryForm setUpForm() {
		return new EditCategoryForm();
	}

	/**
	 * カテゴリ編集ページを表示する.
	 * 
	 * @return カテゴリ編集ページ
	 */
	@RequestMapping("")
	public String index() {
		createCategoryList();
		return "edit_category";
	}

	/**
	 * 既存カテゴリの編集・削除をする.
	 * 
	 * @param form       編集情報
	 * @param deleteFlag 削除フラグ
	 * @param model      リクエストスコープ
	 * @param flash      フラッシュスコープ
	 * @return カテゴリ編集ページ
	 */
	@RequestMapping("/edit-or-delete")
	public String editOrDelete(EditCategoryForm form, boolean deleteFlag, Model model, RedirectAttributes flash) {
		boolean editFormContainBlank = checkBlankInEditForm(form);
		if (!deleteFlag && editFormContainBlank) {
			model.addAttribute("editError", "error:may not be empty");
			return "edit_category";
		}

		if (deleteFlag) {
			return "redirect:/category";
		}

		boolean editionCompleted = editCategoryService.updateExistingCategory(form);
		String message;
		if (editionCompleted) {
			message = " Edition completed!! (" + form.getParentCategoryName() + "/" + form.getChildCategoryName() + "/"
					+ form.getGrandChildCategoryName() + ")";
		} else {
			message = " this category is existing (" + form.getParentCategoryName() + "/" + form.getChildCategoryName()
					+ ")";
		}
		flash.addFlashAttribute("editionCompleted", message);
		return "redirect:/category";
	}

	/**
	 * 新規カテゴリ追加ページを表示する.
	 * 
	 * @return 新規カテゴリ追加ページ
	 */
	@RequestMapping("/add")
	public String toAddPage() {
		createCategoryList();
		return "add_category";
	}

	/**
	 * 新規親カテゴリを追加する.
	 * 
	 * @param form  新規カテゴリ
	 * @param model リクエストスコープ
	 * @param flash フラッシュスコープ
	 * @return 新規カテゴリ追加ページ
	 */
	@RequestMapping("/add-parent")
	public String inputNewParent(EditCategoryForm form, Model model, RedirectAttributes flash) {
		if ("".equals(form.getParentCategoryName())) {
			model.addAttribute("parentError", "error:may not be empty");
			return "add_category";
		}

		boolean additionCompleted = editCategoryService.addNewCategory(form);

		if (additionCompleted) {
			String message = " Addition completed!! ( parentCategory :" + form.getParentCategoryName() + ")";
			flash.addFlashAttribute("AdditionCompleted", message);
		} else {
			String message = "ParentCategory :" + form.getParentCategoryName() + " is existing";
			flash.addFlashAttribute("AdditionCompleted", message);
		}
		return "redirect:/category/add";
	}

	/**
	 * 新規子カテゴリを追加する.
	 * 
	 * @param form  新規カテゴリ
	 * @param model リクエストスコープ
	 * @param flash フラッシュスコープ
	 * @return 新規カテゴリ追加ページ
	 */
	@RequestMapping("/add-child")
	public String inputNewChild(EditCategoryForm form, Model model, RedirectAttributes flash) {
		int errorCount = 0;
		if ("".equals(form.getParentCategoryId())) {
			model.addAttribute("parentIsBlank", "error:select a ParentCategory");
			errorCount++;
		}
		if ("".equals(form.getChildCategoryName()) || form.getChildCategoryName() == null) {
			model.addAttribute("childError", "error:may not be empty");
			errorCount++;
		}
		if (errorCount > 0) {
			return "add_category";
		}

		boolean additionCompleted = editCategoryService.addNewCategory(form);

		if (additionCompleted) {
			String message = " Addition completed!! ( ChildCategory :" + form.getChildCategoryName() + ")";
			flash.addFlashAttribute("AdditionCompleted", message);
		} else {
			String message = "ChildCategory :" + form.getChildCategoryName() + " is existing";
			flash.addFlashAttribute("AdditionCompleted", message);
		}
		return "redirect:/category/add";
	}

	/**
	 * 新規孫カテゴリを追加する.
	 * 
	 * @param form  新規カテゴリ
	 * @param model リクエストスコープ
	 * @param flash フラッシュスコープ
	 * @return 新規カテゴリ追加ページ
	 */
	@RequestMapping("/add-grandchild")
	public String inputNewGrandChild(EditCategoryForm form, Model model, RedirectAttributes flash) {
		int errorCount = 0;
		if ("".equals(form.getParentCategoryId()) || "".equals(form.getChildCategoryId())) {
			model.addAttribute("childIsBlank", "error:select ParentCategory and ChildCategory");
			errorCount++;
		}
		if ("".equals(form.getGrandChildCategoryName()) || form.getGrandChildCategoryName() == null) {
			model.addAttribute("grandChildError", "error:may not be empty");
			errorCount++;
		}
		if (errorCount > 0) {
			return "add_category";
		}

		boolean additionCompleted = editCategoryService.addNewCategory(form);

		if (additionCompleted) {
			String message = " Addition completed!! ( GrandChildCategory :" + form.getGrandChildCategoryName() + ")";
			flash.addFlashAttribute("AdditionCompleted", message);
		} else {
			String message = "GrandChildCategory :" + form.getGrandChildCategoryName() + " is existing";
			flash.addFlashAttribute("AdditionCompleted", message);
		}
		return "redirect:/category/add";
	}

	/**
	 * 編集フォームの未入力チェック.
	 * 
	 * @param form 編集フォーム入力内容
	 * @return 未入力欄あり:true, なし：false
	 */
	public boolean checkBlankInEditForm(EditCategoryForm form) {
		if (!"".equals(form.getParentCategoryId()) && "".equals(form.getParentCategoryName())) {
			return true;
		}
		if (!"".equals(form.getChildCategoryId()) && "".equals(form.getChildCategoryName())) {
			return true;
		}
		if (!"".equals(form.getGrandChildCategoryId()) && "".equals(form.getGrandChildCategoryName())) {
			return true;
		}
		return false;
	}

	/**
	 * カテゴリリストを作成する.
	 */
	public void createCategoryList() {
		List<Category> categoryList = categoryService.getAllCategory();
		List<Category> parentCategoryList = categoryService.createParentCategoryList(categoryList);
		session.setAttribute("parentCategoryList", parentCategoryList);
		List<Category> childCategoryList = categoryService.createChildCategoryList(categoryList);
		session.setAttribute("childCategoryList", childCategoryList);
		List<Category> grandChildCategoryList = categoryService.createGrandChildCategoryList(categoryList);
		session.setAttribute("grandChildCategoryList", grandChildCategoryList);
	}
}
