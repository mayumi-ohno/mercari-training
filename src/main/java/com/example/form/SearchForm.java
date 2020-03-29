package com.example.form;

/**
 * 曖昧検索の入力値を表すフォーム.
 * 
 * @author mayumiono
 *
 */
public class SearchForm {

	/** 商品名 */
	private String name;
	/** 親カテゴリ */
	private String parentCategoryId;
	/** 子カテゴリ */
	private String childCategoryId;
	/** 孫カテゴリ */
	private String grandChildCategoryId;
	/** ブランド */
	private String brand;

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
		this.childCategoryId = childCategoryId;
	}

	public String getGrandChildCategoryId() {
		return grandChildCategoryId;
	}

	public void setGrandChildCategoryId(String grandChildCategoryId) {
		this.grandChildCategoryId = grandChildCategoryId;
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

	@Override
	public String toString() {
		return "FuzzySearchForm [name=" + name + ", parentCategoryId=" + parentCategoryId + ", childCategoryId="
				+ childCategoryId + ", grandChildCategoryId=" + grandChildCategoryId + ", brand=" + brand + "]";
	}

	/**
	 * 検索値の入力有無を確認する.
	 * 
	 * @return 入力あり：true, 入力なし:false
	 */
	public boolean existValues() {
		if (this.name == null && this.parentCategoryId == null && this.childCategoryId == null
				&& this.grandChildCategoryId == null && this.brand == null) {
			return false;
		}
		if ("".equals(this.name) && "".equals(this.parentCategoryId) && "".equals(this.childCategoryId)
				&& "".equals(this.grandChildCategoryId) && "".equals(this.brand)) {
			return false;
		}
		return true;
	}
}
