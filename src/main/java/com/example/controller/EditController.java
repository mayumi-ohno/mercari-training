package com.example.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.Item;
import com.example.form.EditItemDetailForm;
import com.example.service.EditDetailService;

/**
 * 商品情報編集を表示するコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/edit")
public class EditController {

	@Autowired
	private HttpSession session;

	@Autowired
	private EditDetailService editDetailService;

	@ModelAttribute
	private EditItemDetailForm setUpForm() {
		EditItemDetailForm editItemDetailForm = new EditItemDetailForm();
		Item item = (Item) session.getAttribute("item");
		BeanUtils.copyProperties(item, editItemDetailForm);
		editItemDetailForm.setParentCategoryId(String.valueOf(item.getParentCategoryId()));
		editItemDetailForm.setChildCategoryId(String.valueOf(item.getChildCategoryId()));
		editItemDetailForm.setGrandChildCategoryId(String.valueOf(item.getGrandChildCategoryId()));
		editItemDetailForm.setCondition(String.valueOf(item.getCondition()));
		editItemDetailForm.setPrice(String.valueOf(item.getPrice()));
		editItemDetailForm.setImage(null);
		return editItemDetailForm;
	}

	/**
	 * 編集ページを表示する.
	 * 
	 * @param itemId 商品ID
	 * @param model  リクエストスコープ
	 * @return 商品情報編集ページ
	 */
	@RequestMapping("")
	public String showDetail() {
		return "edit";
	}

	/**
	 * 商品情報を更新する.
	 * 
	 * @param form   入力情報
	 * @param result 入力チェク
	 * @return 詳細画面へのリダイレクト
	 * @throws IOException
	 */
	@RequestMapping("/input")
	public String updateDetail(@Validated EditItemDetailForm form, BindingResult result, RedirectAttributes flash)
			throws IOException {

		// 画像ファイル形式チェック
		String fileExtension = null;
		try {
			MultipartFile imageFile = form.getImage();
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
			return "edit";
		}
		editDetailService.edit(form);
		flash.addFlashAttribute("completionOfEditing", "Editing completed!!");
		return "redirect:/detail";
	}

	/**
	 * 編集ページの"back"ボタンを押下した際にリファラ情報を作成する.
	 * 
	 * @param request クライアントからのリクエスト
	 * @return 詳細画面
	 */
	@RequestMapping("/referer")
	public String createReferer(HttpServletRequest request) {
		session.setAttribute("referer", request.getHeader("REFERER"));
		return "forward:/detail";
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
