package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.form.AddItemForm;
import com.example.service.AddItemService;

/**
 * 商品追加をするコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/add")
public class AddItemController {

	@Autowired
	private AddItemService addItemService;

	@ModelAttribute
	public AddItemForm setUpForm() {
		return new AddItemForm();
	}

	/**
	 * 商品追加画面を表示する.
	 * 
	 * @return 商品追加画面
	 */
	@RequestMapping("")
	public String index() {
		return "add";
	}

	/**
	 * 商品を追加する.
	 * 
	 * @param form   入力情報
	 * @param result 入力チェク
	 * @return 詳細画面へのリダイレクト
	 */
	@RequestMapping("/input")
	public String insert(@Validated AddItemForm form, BindingResult result, RedirectAttributes flash) {
		if (result.hasErrors()) {
			return "add";
		}
		addItemService.addItem(form);
		flash.addFlashAttribute("completionOfAddition", "Addition completed!!");
		return "redirect:/items";
	}

}
