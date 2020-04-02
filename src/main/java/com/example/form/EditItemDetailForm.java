package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

/**
 * 商品詳細の更新情報を表すフォーム.
 * 
 * @author mayumiono
 *
 */
public class EditItemDetailForm {
	/** 商品ID */
	private String id;
	/** 商品名 */
	@NotBlank(message = "error:may not be empty")
	private String name;
	/** コンディション */
	@NotBlank(message = "error:may not be empty")
	private String condition;
	/** 親カテゴリID */
	@Size(min = 1, message = "error:may not be empty")
	private String parentCategoryId;
	/** 子カテゴリID */
	@Size(min = 1, message = "error:may not be empty")
	private String childCategoryId;
	/** 孫カテゴリID */
	@Size(min = 1, message = "error:may not be empty")
	private String grandChildCategoryId;
	/** ブランド名 */
	private String brand;
	/** 価格 */
	@Pattern(regexp = "^([1-9]\\d*|0)(\\.\\d+)?$", message = "error:enter Decimal or Integer in price field")
	private String price;
	/** 商品説明 */
	@NotBlank(message = "error:may not be empty")
	private String description;
	/** 画像 */
	private MultipartFile image;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getChildCategoryId() {
		return childCategoryId;
	}

	public void setChildCategoryId(String childCategoryId) {
		if ("".equals(this.parentCategoryId)) {
			this.childCategoryId = "";
		} else {
			this.childCategoryId = childCategoryId;
		}
	}

	public String getGrandChildCategoryId() {
		return grandChildCategoryId;
	}

	public void setGrandChildCategoryId(String grandChildCategoryId) {
		if ("".equals(this.parentCategoryId) || "".equals(this.childCategoryId)) {
			this.grandChildCategoryId = "";
		} else {
			this.grandChildCategoryId = grandChildCategoryId;
		}
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "EditItemDetailForm [id=" + id + ", name=" + name + ", condition=" + condition + ", parentCategoryId="
				+ parentCategoryId + ", childCategoryId=" + childCategoryId + ", grandChildCategoryId="
				+ grandChildCategoryId + ", brand=" + brand + ", price=" + price + ", description=" + description
				+ ", image=" + image + "]";
	}

}
