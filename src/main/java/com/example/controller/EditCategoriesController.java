package com.example.controller;

import java.util.ArrayList;
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

		// 削除処理ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー

		Integer itemsBelongingToThisCategory = null;

		if (deleteFlag) {
			boolean existLowerHerarchty = checkLowerHerarchy(form, model);
			if (existLowerHerarchty) {
				model.addAttribute("deleteError",
						"error: CANNOT DELETE because Lower-Herarchy-Categories are existing");
				return "edit_category";
			}
			itemsBelongingToThisCategory = editCategoryService.deleteCategory(form);
		}

		if (itemsBelongingToThisCategory != null && itemsBelongingToThisCategory > 0) {
			model.addAttribute("deleteError",
					"error: CANNNOT DELETE because some items are belonging to this category");
			return "edit_category";
		}

		if (itemsBelongingToThisCategory != null && itemsBelongingToThisCategory == 0) {
			flash.addFlashAttribute("editionCompleted", "Delete Completed!!");
			return "redirect:/category";
		}

		// 編集処理ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
		boolean editFormContainBlank = checkBlankInEditForm(form);
		if (editFormContainBlank) {
			model.addAttribute("editError", "error:may not be empty");
			return "edit_category";
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
			model.addAttribute("parentIsBlank", "error:may not be empty");
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
			model.addAttribute("childIsBlank", "error:error:may not be empty");
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

	/**
	 * 削除対象カテゴリに属する下層カテゴリの存在を確認する.
	 * 
	 * @param form 削除対象カテゴリ
	 * @return 存在する:true, 存在しない:false
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/check-lower-herarchy")
	public boolean checkLowerHerarchy(EditCategoryForm form, Model model) {
		boolean existParentCategoryId = !"".equals(form.getParentCategoryId()) && form.getParentCategoryId() != null;
		boolean existChildCategoryId = !"".equals(form.getChildCategoryId()) && form.getChildCategoryId() != null;
		boolean existGrandChildCategoryId = !"".equals(form.getGrandChildCategoryId())
				&& form.getGrandChildCategoryId() != null;

		String categoryId = null;
		List<Category> categoriesInLowerHerarchy = new ArrayList<>();
		if (existGrandChildCategoryId) {
			return false;
		} else if (existChildCategoryId) {
			categoryId = form.getChildCategoryId();
			categoriesInLowerHerarchy = (List<Category>) session.getAttribute("grandChildCategoryList");
		} else if (existParentCategoryId) {
			categoryId = form.getParentCategoryId();
			categoriesInLowerHerarchy = (List<Category>) session.getAttribute("childCategoryList");
		}

		for (Category category : categoriesInLowerHerarchy) {
			String parentId = String.valueOf(category.getParent());
			if (parentId.equals(categoryId)) {
				return true;
			}
		}

		return false;
	}
}
