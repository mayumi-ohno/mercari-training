package com.example.form;

import java.util.Arrays;

/**
 * セール設定情報を表すフォーム.
 * 
 * @author mayumiono
 *
 */
public class SaleForm {

	/** 商品ID */
	private Integer[] itemIdList;
	/** セール開始日 */
	private String start;
	/** セール終了日 */
	private String end;
	/** 割引率 */
	private String discountRate;

	public Integer[] getItemIdList() {
		return itemIdList;
	}

	public void setItemIdList(Integer[] itemIdList) {
		this.itemIdList = itemIdList;
	}

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

	@Override
	public String toString() {
		return "SaleForm [itemIdList=" + Arrays.toString(itemIdList) + ", start=" + start + ", end=" + end
				+ ", discountRate=" + discountRate + "]";
	}

}
