package com.example;

import com.example.domain.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

/**
 * csv出力用の商品情報を表すドメイン.
 * 
 * @author mayumiono
 *
 */
@JsonPropertyOrder({ "ID", "商品名", "状態", "カテゴリ", "ブランド", "価格", "商品説明" })
@Data
public class ItemForDownload {

	/** 商品ID */
	@JsonProperty("ID")
	private Integer id;
	/** 商品名 */
	@JsonProperty("商品名")
	private String name;
	/** コンディション */
	@JsonProperty("状態")
	private Integer condition;
	/** カテゴリ名 */
	@JsonProperty("カテゴリ")
	private String category;
	/** ブランド名 */
	@JsonProperty("ブランド")
	private String brand;
	/** 価格 */
	@JsonProperty("価格")
	private Double price;
	/** 商品説明 */
	@JsonProperty("商品説明")
	private String description;

	public ItemForDownload() {
	}

	public ItemForDownload(Item item) {
		this.id = item.getId();
		this.name = item.getName();
		this.condition = item.getCondition();
		this.category = item.getParentCategory() + "/" + item.getChildCategory() + "/" + item.getGrandChildCategory();
		this.brand = item.getBrand();
		this.price = item.getPrice();
		this.description = item.getDescription();
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
		this.name = name;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ItemForDownload [id=" + id + ", name=" + name + ", condition=" + condition + ", category=" + category
				+ ", brand=" + brand + ", price=" + price + ", description=" + description + "]";
	}

}
