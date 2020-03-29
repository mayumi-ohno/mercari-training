package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.form.EditItemDetailForm;
import com.example.service.EditDetailService;

/**
 * 商品情報編集を表示するコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/edit")
public class EditController {

	@Autowired
	private HttpSession session;

	@Autowired
	private EditDetailService editDetailService;

	@ModelAttribute
	private EditItemDetailForm setUpForm() {
		EditItemDetailForm editItemDetailForm = new EditItemDetailForm();
		Item item = (Item) session.getAttribute("item");
		BeanUtils.copyProperties(item, editItemDetailForm);
		editItemDetailForm.setParentCategoryId(String.valueOf(item.getParentCategoryId()));
		editItemDetailForm.setChildCategoryId(String.valueOf(item.getChildCategoryId()));
		editItemDetailForm.setGrandChildCategoryId(String.valueOf(item.getGrandChildCategoryId()));
		editItemDetailForm.setCondition(String.valueOf(item.getCondition()));
		editItemDetailForm.setPrice(String.valueOf(item.getPrice()));
		return editItemDetailForm;
	}

	/**
	 * 編集ページを表示する.
	 * 
	 * @param itemId 商品ID
	 * @param model  リクエストスコープ
	 * @return 商品情報編集ページ
	 */
	@RequestMapping("")
	public String showDetail() {
		return "edit";
	}

	/**
	 * 商品情報を更新する.
	 * 
	 * @param form   入力情報
	 * @param result 入力チェク
	 * @return 詳細画面へのリダイレクト
	 */
	@RequestMapping("/input")
	public String updateDetail(@Validated EditItemDetailForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "edit";
		}
		editDetailService.edit(form);
		return "redirect:/edit/to-detail";
	}

	/**
	 * 詳細画面へ遷移する.
	 * 
	 * @return 商品詳細画面
	 */
	@RequestMapping("/to-detail")
	public String toDetailPage() {
		return "forward:/detail";
	}

	/**
	 * 編集ページの"back"ボタンを押下した際にリファラ情報を作成する.
	 * 
	 * @param request クライアントからのリクエスト
	 * @return 詳細画面
	 */
	@RequestMapping("/referer")
	public String createReferer(HttpServletRequest request) {
		session.setAttribute("referer", request.getHeader("REFERER"));
		System.out.println(session.getAttribute("referer").toString());
		return "forward:/detail";
	}

}
