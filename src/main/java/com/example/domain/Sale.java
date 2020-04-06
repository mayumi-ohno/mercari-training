package com.example.domain;

import java.sql.Date;

/**
 * セール情報を表すフォーム.
 * 
 * @author mayumiono
 *
 */
public class Sale {

	/** 商品ID */
	private Integer itemId;
	/** セール開始日 */
	private Date start;
	/** セール終了日 */
	private Date end;
	/** 割引率 */
	private Integer discountRate;
	/** 商品名 */
	private String name;
	/** 親カテゴリ */
	private Integer parentCategoryId;
	/** 子カテゴリ */
	private Integer childCategoryId;
	/** 孫カテゴリ */
	private Integer grandChildCategoryId;
	/** ブランド */
	private String brand;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Integer getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public Integer getChildCategoryId() {
		return childCategoryId;
	}

	public void setChildCategoryId(Integer childCategoryId) {
		this.childCategoryId = childCategoryId;
	}

	public Integer getGrandChildCategoryId() {
		return grandChildCategoryId;
	}

	public void setGrandChildCategoryId(Integer grandChildCategoryId) {
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
		return "Sale [itemId=" + itemId + ", start=" + start + ", end=" + end + ", discountRate=" + discountRate
				+ ", name=" + name + ", parentCategoryId=" + parentCategoryId + ", childCategoryId=" + childCategoryId
				+ ", grandChildCategoryId=" + grandChildCategoryId + ", brand=" + brand + "]";
	}

}
