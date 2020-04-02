package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.User;
import com.example.form.AddUserForm;
import com.example.form.EditUserForm;
import com.example.service.ShowAndEditUserService;

@Controller
@RequestMapping("/user")
public class UserManagerController {

	@Autowired
	private ShowAndEditUserService showAndEditUserService;

	@ModelAttribute
	public EditUserForm setupEditForm() {
		return new EditUserForm();
	}

	@ModelAttribute
	public AddUserForm setupAddForm() {
		return new AddUserForm();
	}

	/**
	 * ユーザー管理ページを表示する.
	 * 
	 * @return ユーザー管理ページ
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<User> userList = showAndEditUserService.getAllUsers();
		model.addAttribute("userList", userList);
		return "user_list";
	}

	/**
	 * 既存ユーザー情報を更新する.
	 * 
	 * @param form   ユーザー情報
	 * @param result 入力チェック
	 * @param model  リクエストスコープ
	 * @return ユーザー一覧ページ
	 */
	@RequestMapping("/edit")
	public String edit(@Validated EditUserForm form, BindingResult result, Model model, RedirectAttributes flash) {

		boolean emailDuplicating = showAndEditUserService.checkEmailDuplication(form);
		if (emailDuplicating) {
			result.rejectValue("email", null, "error: this email-address is existing");
		}

		if (result.hasErrors()) {
			model.addAttribute("errorUserId", form.getId());
			List<User> userList = showAndEditUserService.getAllUsers();
			model.addAttribute("userList", userList);
			return "user_list";
		}

		showAndEditUserService.updateUserInfo(form);
		String message = "Editing Completed!! ( userId:" + form.getId() + " )";
		flash.addFlashAttribute("editingCompleted", message);
		return "redirect:/user";
	}

	/**
	 * 新規ユーザー登録.
	 * 
	 * @param form   ユーザー情報
	 * @param result 入力チェック
	 * @param model  リクエストスコープ
	 * @return ユーザー一覧ページ
	 */
	@RequestMapping("/add")
	public String add(@Validated AddUserForm form, BindingResult result, Model model, RedirectAttributes flash) {

		boolean emailDuplicating = showAndEditUserService.checkEmailDuplication(form);
		if (emailDuplicating) {
			result.rejectValue("email", null, "error: this email-address is existing");
		}

		if (result.hasErrors()) {
			List<User> userList = showAndEditUserService.getAllUsers();
			model.addAttribute("userList", userList);
			return "user_list";
		}

		showAndEditUserService.addUser(form);
		String message = "Addition Completed!!";
		flash.addFlashAttribute("additionCompleted", message);
		return "redirect:/user";
	}

	/**
	 * ユーザー情報を削除する.
	 * 
	 * @param userId ユーザーID
	 * @param flash  フラッシュスコープ
	 * @return ユーザー一覧ページ
	 */
	@RequestMapping("/delete")
	public String delete(String userId, RedirectAttributes flash) {
		showAndEditUserService.deleteUser(Integer.parseInt(userId));
		String message = "Delete Completed!! ( userId: " + userId + " )";
		flash.addFlashAttribute("editingCompleted", message);
		return "redirect:/user";
	}
}
