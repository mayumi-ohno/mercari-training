package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.service.DeleteItemsService;

/**
 * 商品削除をするコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/delete-items")
public class DeleteItemsController {

	@Autowired
	private DeleteItemsService deleteItemsService;

	/**
	 * 商品を削除する.
	 * 
	 * @param itemIdList 削除対象の商品ID
	 * @return 商品一覧
	 */
	@RequestMapping("")
	public String delete(Integer[] itemIdList, RedirectAttributes flash) {
		deleteItemsService.deleteItems(itemIdList);
		flash.addFlashAttribute("completionOfDelete", "Delete completed!!");
		return "redirect:/items";
	}

}
