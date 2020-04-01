package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理者用ページを表示するコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/admin")
public class ShowAdminPageController {

	/**
	 * 管理者用ページを表示する.
	 * 
	 * @return 管理者用ページ
	 */
	@RequestMapping("")
	public String index() {
		return "admin_page";
	}

}
