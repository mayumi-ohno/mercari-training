package com.example.controller;

import javax.servlet.http.HttpSession;

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

	@Autowired
	private HttpSession session;

	/**
	 * 商品詳細を表示する.
	 * 
	 * @param itemId 商品ID
	 * @param model  リクエストスコープ
	 * @return 商品詳細ページ
	 */
	@RequestMapping("")
	public String showDetail(String itemId, Model model) {
		String url = (String) session.getAttribute("referer");
		session.removeAttribute("referer");

		if (itemId == null) {
			Item item = (Item) session.getAttribute("item");
			itemId = String.valueOf(item.getId());
		}

		// 編集ページから遷移してきた場合、商品詳細情報をセッション格納済
		if (!"http://localhost:8080/edit".equals(url)) {
			Item item = showDetailService.showDetail(Integer.parseInt(itemId));
			session.setAttribute("item", item);
		}

		return "detail";
	}

}
