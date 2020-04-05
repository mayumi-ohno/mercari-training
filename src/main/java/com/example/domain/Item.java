package com.example.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

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
	/** 画像 */
	private String image;
	/** セール情報 */
	private Sale sale;

	/**
	 * 値引き予定の有無を返す.
	 * 
	 * @return 予定あり：true, 予定なし：false
	 */
	public boolean getToBeDiscount() {
		LocalDate today = LocalDate.now();
		LocalDate start;
		try {
			start = sale.getStart().toLocalDate();
		} catch (Exception e) {
			return false;
		}
		if (today.isBefore(start)) {
			return true;
		}
		return false;
	}

	/**
	 * セール中か否かを返す
	 * 
	 * @return セール中：true, セール中ではない：false
	 */
	public boolean getDiscounting() {
		LocalDate today = LocalDate.now();
		LocalDate start;
		LocalDate end;
		try {
			start = sale.getStart().toLocalDate();
			end = sale.getEnd().toLocalDate();
		} catch (Exception e) {
			return false;
		}
		boolean saleStarted = today.isAfter(start) || today.isEqual(start);
		boolean saleNotEnded = today.isBefore(end) || today.isEqual(end);
		if (saleStarted && saleNotEnded) {
			return true;
		}
		return false;
	}

	/**
	 * セール価格を返す.
	 * 
	 * @return セール価格
	 */
	public Double getDiscountPrice() {
		LocalDate today = LocalDate.now();
		LocalDate start;
		LocalDate end;
		try {
			start = sale.getStart().toLocalDate();
			end = sale.getEnd().toLocalDate();
		} catch (Exception e) {
			return this.price;
		}
		boolean saleStarted = today.isAfter(start) || today.isEqual(start);
		boolean saleNotEnded = today.isBefore(end) || today.isEqual(end);
		if (saleStarted && saleNotEnded) {
			BigDecimal originalPrice = BigDecimal.valueOf(this.price);
			BigDecimal discountRate = BigDecimal.valueOf(100 - this.sale.getDiscountRate());
			discountRate = discountRate.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_UP);
			BigDecimal discountPriceBD = originalPrice.multiply(discountRate);
			discountPriceBD = discountPriceBD.setScale(1, BigDecimal.ROUND_UP);
			return discountPriceBD.doubleValue();
		}
		return this.price;
	}

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", condition=" + condition + ", grandChildCategoryId="
				+ grandChildCategoryId + ", grandChildCategory=" + grandChildCategory + ", childCategoryId="
				+ childCategoryId + ", childCategory=" + childCategory + ", parentCategoryId=" + parentCategoryId
				+ ", parentCategory=" + parentCategory + ", brand=" + brand + ", price=" + price + ", shipping="
				+ shipping + ", description=" + description + ", image=" + image + ", sale=" + sale + "]";
	}

}
