package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowDetailService;

/**
 * 商品詳細を表示するコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/detail")
public class ShowDetailController {

	@Autowired
	private ShowDetailService showDetailService;

	/**
	 * 商品詳細を表示する.
	 * 
	 * @param itemId 商品ID
	 * @param model  リクエストスコープ
	 * @return 商品詳細ページ
	 */
	@RequestMapping("")
	public String showDetail(String itemId, Model model) {
		Item item = showDetailService.showDetail(Integer.parseInt(itemId));
		model.addAttribute("item", item);
		return "detail";
	}

}
