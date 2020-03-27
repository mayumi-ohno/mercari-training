package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.RegisterUserForm;
import com.example.service.RegisterUserService;

/**
 * ユーザー登録処理のコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterUserController {

	@ModelAttribute
	public RegisterUserForm setupForm() {
		return new RegisterUserForm();
	}

	@Autowired
	private RegisterUserService registerUserService;

	/**
	 * ユーザー登録ページの表示.
	 * 
	 * @return ユーザー登録ページ
	 */
	@RequestMapping("")
	public String index() {
		return "register";
	}

	/**
	 * 登録処理を行う.
	 * 
	 * @param form   新規ユーザー情報
	 * @param result 入力値チェック
	 * @return ログインページへのリダイレクト（不正入力時はユーザー登録ページ）
	 */
	@RequestMapping("/input")
	public String inputData(@Validated RegisterUserForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "register";
		}
		registerUserService.register(form);
		return "redirect:/register/to-login";
	}

	@RequestMapping("/to-login")
	public String toLogin() {
		return "forward:/";
	}
}
