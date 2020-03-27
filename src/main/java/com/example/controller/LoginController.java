package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ログインページ遷移を行うコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/")
public class LoginController {

	/**
	 * ログインページを表示する.
	 * 
	 * @return ログインページ
	 */
	@RequestMapping("")
	public String showLoginPage() {
		return "login";
	}

	/**
	 * ログイン失敗時にエラー文を表示する.
	 * 
	 * @param model リクエストスコープ
	 * @return ログインページ
	 */
	@RequestMapping("/login_error")
	public String loginError(Model model) {
		model.addAttribute("loginError", "error:failed to login");
		return "login";
	}

}
