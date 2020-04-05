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

	@Override
	public String toString() {
		return "Sale [itemIdList=" + itemId + ", start=" + start + ", end=" + end + ", discountRate=" + discountRate
				+ "]";
	}

}
