package com.example.domain;

/**
 * 商品を表すドメイン.
 * 
 * @author mayumiono
 *
 */
public class Item {

	/** 商品ID */
	private Integer id;
	/** 商品名 */
	private String name;
	/** コンディション */
	private Integer condition;
	/** 孫カテゴリID */
	private Integer grandChildCategoryId;
	/** 孫カテゴリ */
	private String grandChildCategory;
	/** 子カテゴリID */
	private Integer childCategoryId;
	/** 子カテゴリ */
	private String childCategory;
	/** 親カテゴリID */
	private Integer parentCategoryId;
	/** 親カテゴリ */
	private String parentCategory;
	/** ブランド名 */
	private String brand;
	/** 価格 */
	private Double price;
	/** 出荷状況 */
	private Integer shipping;
	/** 商品説明 */
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public Integer getGrandChildCategoryId() {
		return grandChildCategoryId;
	}

	public void setGrandChildCategoryId(Integer grandChildCategoryId) {
		this.grandChildCategoryId = grandChildCategoryId;
	}

	public String getGrandChildCategory() {
		return grandChildCategory;
	}

	public void setGrandChildCategory(String grandChildCategory) {
		this.grandChildCategory = grandChildCategory;
	}

	public Integer getChildCategoryId() {
		return childCategoryId;
	}

	public void setChildCategoryId(Integer childCategoryId) {
		this.childCategoryId = childCategoryId;
	}

	public String getChildCategory() {
		return childCategory;
	}

	public void setChildCategory(String childCategory) {
		this.childCategory = childCategory;
	}

	public Integer getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Integer parentCategoryId) {
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
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
