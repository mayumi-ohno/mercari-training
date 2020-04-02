package com.example.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
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
	 * @throws IOException
	 */
	@RequestMapping("/input")
	public String insert(@Validated AddItemForm form, BindingResult result, RedirectAttributes flash)
			throws IOException {

		// 画像ファイル形式チェック
		MultipartFile imageFile = form.getImage();
		String fileExtension = null;
		try {
			fileExtension = getExtension(imageFile.getOriginalFilename());
			if (!"jpg".equals(fileExtension) && !"png".equals(fileExtension) && !"gif".equals(fileExtension)) {
				result.rejectValue("image", "", "拡張子は.jpg/.png/.gifのみに対応しています");
			}
		} catch (NullPointerException e) {
			// 画像ファイルがアップロードされなかった場合、なにもしない
		} catch (FileNotFoundException e) {
			// ファイル名に拡張子が見当たらない場合、エラー
			result.rejectValue("image", "", "拡張子は.jpg/.png/.gifのみに対応しています");
		}

		if (result.hasErrors()) {
			return "add";
		}

		addItemService.addItem(form);
		flash.addFlashAttribute("completionOfAddition", "Addition completed!!");
		return "redirect:/items";
	}

	/*
	 * <<<<<<< HEAD ファイル名から拡張子を返す. ======= ファイル名から拡張子を返します. >>>>>>>
	 * 2f205365d037c254f6d538d6eb5caf4c24c54344
	 * 
	 * @param originalFileName ファイル名
	 * 
	 * @return .を除いたファイルの拡張子
	 */
	private String getExtension(String originalFileName) throws NullPointerException, FileNotFoundException {
		if (originalFileName == null || "".equals(originalFileName)) {
			throw new NullPointerException();
		}
		int point = originalFileName.lastIndexOf(".");
		if (point == -1) {
			// ファイル名に拡張子がついていない場合は例外とする
			throw new FileNotFoundException();
		}
		return originalFileName.substring(point + 1);
	}

}
