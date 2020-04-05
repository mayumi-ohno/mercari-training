package com.example.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.form.SaleForm;
import com.example.form.SearchForm;
import com.example.service.SaleSettingService;
import com.example.service.ShowListService;

/**
 * セールの設定をするコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/sale")
public class SaleSettingController {

	/** 1ページに表示する商品数 */
	private final static Integer VIEW_SIZE = 30;

	@Autowired
	private ShowListService showListService;

	@Autowired
	private SaleSettingService saleSettingService;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public SearchForm setUpSearchForm() {
		return new SearchForm();
	}

	@ModelAttribute
	public SaleForm setUpSaleForm() {
		return new SaleForm();
	}

	/**
	 * 商品一覧を表示する.
	 * 
	 * @return 商品一覧ページ
	 */
	@RequestMapping("")
	public String index(SearchForm form, Integer page, Model model, @AuthenticationPrincipal LoginUser loginUser) {

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

		// セール設定フォームの最小日付設定用
		LocalDate today = LocalDate.now();
		model.addAttribute("today", today);

		return "setting_sale";
	}

	/**
	 * 選択した商品をセールにする.
	 * 
	 * @param form  セール情報
	 * @param flash フラッシュスコープ
	 * @return 商品一覧
	 */
	@RequestMapping("/discount")
	public String discount(SaleForm form, Model model, RedirectAttributes flash) {
		Integer start = Integer.valueOf(form.getStart().replaceAll("-", ""));
		Integer end = Integer.valueOf(form.getEnd().replaceAll("-", ""));
		if (end < start) {
			flash.addFlashAttribute("dateError", "error: start date must be less than end date");
			return "redirect:/sale";
		}
		saleSettingService.setting(form);
		flash.addFlashAttribute("completionOfsetting", "Setting completed!!");
		return "redirect:/sale";
	}

	/**
	 * 選択した商品をセール解除する.
	 * 
	 * @param itemIdList 削除対象の商品ID
	 * @return 商品一覧
	 */
	@RequestMapping("/cancel")
	public String cancelDiscount(Integer[] itemIdList, RedirectAttributes flash) {
		saleSettingService.cancel(itemIdList);
		flash.addFlashAttribute("completionOfsetting", "Setting completed!!");
		return "redirect:/sale";
	}

}
