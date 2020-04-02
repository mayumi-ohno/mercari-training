package com.example.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.Item;
import com.example.form.EditItemDetailForm;
import com.example.repository.ItemRepository;

/**
 * 商品情報編集処理をするサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class EditDetailService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品情報を編集する.
	 * 
	 * @param form 商品情報
	 */
	public void edit(EditItemDetailForm form) throws IOException {
		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		item.setId(Integer.parseInt(form.getId()));
		item.setGrandChildCategoryId(Integer.parseInt(form.getGrandChildCategoryId()));
		item.setCondition(Integer.parseInt(form.getCondition()));
		item.setPrice(Double.parseDouble(form.getPrice()));

		// 画像ファイルをBase64形式にエンコードしてドメインへセット
		if (form.getImage() != null) {
			MultipartFile imageFile = form.getImage();
			String originalFileName = imageFile.getOriginalFilename();
			int point = originalFileName.lastIndexOf(".");
			String fileExtension = originalFileName.substring(point + 1);
			String base64FileString = Base64.getEncoder().encodeToString(imageFile.getBytes());
			if ("jpg".equals(fileExtension)) {
				base64FileString = "data:image/jpeg;base64," + base64FileString;
			} else if ("png".equals(fileExtension)) {
				base64FileString = "data:image/png;base64," + base64FileString;
			} else if ("gif".equals(fileExtension)) {
				base64FileString = "data:image/gif;base64," + base64FileString;
			}
			item.setImage(base64FileString);
		}
		itemRepository.update(item);
	}

}
