package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	@NotBlank(message = "Enter name")
	private String name;
	/** コンディション */
	private String condition;
	/** 孫カテゴリID */
	@Size(min = 1, message = "Select a category")
	private String grandChildCategoryId;
	/** 孫カテゴリ */
	private String grandChildCategory;
	/** 子カテゴリID */
	@Size(min = 1, message = "Select a category")
	private String childCategoryId;
	/** 子カテゴリ */
	private String childCategory;
	/** 親カテゴリID */
	@Size(min = 1, message = "Select a category")
	private String parentCategoryId;
	/** 親カテゴリ */
	private String parentCategory;
	/** ブランド名 */
	private String brand;
	/** 価格 */
	@Pattern(regexp = "^([1-9]\\d*|0)(\\.\\d+)?$", message = "Enter Decimal or Integer in price field")
	private String price;
	/** 出荷状況 */
	private String shipping;
	/** 商品説明 */
	@NotBlank(message = "Enter description")
	private String description;

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
		if ("null".equals(name)) {
			this.name = null;
		} else {
			this.name = name;
		}
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getGrandChildCategoryId() {
		return grandChildCategoryId;
	}

	public void setGrandChildCategoryId(String grandChildCategoryId) {
		this.grandChildCategoryId = grandChildCategoryId;
	}

	public String getGrandChildCategory() {
		return grandChildCategory;
	}

	public void setGrandChildCategory(String grandChildCategory) {
		this.grandChildCategory = grandChildCategory;
	}

	public String getChildCategoryId() {
		return childCategoryId;
	}

	public void setChildCategoryId(String childCategoryId) {
		this.childCategoryId = childCategoryId;
	}

	public String getChildCategory() {
		return childCategory;
	}

	public void setChildCategory(String childCategory) {
		this.childCategory = childCategory;
	}

	public String getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		if ("null".equals(brand)) {
			this.brand = null;
		} else {
			this.brand = brand;
		}
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", condition=" + condition + ", grandChildCategoryId="
				+ grandChildCategoryId + ", grandChildCategory=" + grandChildCategory + ", childCategoryId="
				+ childCategoryId + ", childCategory=" + childCategory + ", parentCategoryId=" + parentCategoryId
				+ ", parentCategory=" + parentCategory + ", brand=" + brand + ", price=" + price + ", shipping="
				+ shipping + ", description=" + description + "]";
	}

}
