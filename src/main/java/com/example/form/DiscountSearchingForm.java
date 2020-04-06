package com.example.form;

/**
 * セール情報を表すフォーム
 * 
 * @author mayumiono
 *
 */
public class DiscountSearchingForm {

	/** セール開始日 */
	private String start;
	/** セール終了日 */
	private String end;
	/** 割引率 */
	private String discountRate;
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

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "DiscountSearchingForm [start=" + start + ", end=" + end + ", discountRate=" + discountRate + ", name="
				+ name + ", parentCategoryId=" + parentCategoryId + ", childCategoryId=" + childCategoryId
				+ ", grandChildCategoryId=" + grandChildCategoryId + ", brand=" + brand + "]";
	}

}
