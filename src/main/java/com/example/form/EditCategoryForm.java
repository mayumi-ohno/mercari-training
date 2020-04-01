package com.example.form;

/**
 * カテゴリの編集内容を表すフォーム.
 * 
 * @author mayuimiono
 *
 */
public class EditCategoryForm {

	/** 親カテゴリID */
	private String parentCategoryId;
	/** 親カテゴリ名 */
	private String parentCategoryName;
	/** 子カテゴリID */
	private String childCategoryId;
	/** 子カテゴリ名 */
	private String childCategoryName;
	/** 孫カテゴリID */
	private String grandChildCategoryId;
	/** 孫カテゴリ名 */
	private String grandChildCategoryName;

	public String getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getParentCategoryName() {
		return parentCategoryName;
	}

	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}

	public String getChildCategoryId() {
		return childCategoryId;
	}

	public void setChildCategoryId(String childCategoryId) {
		this.childCategoryId = childCategoryId;
	}

	public String getChildCategoryName() {
		return childCategoryName;
	}

	public void setChildCategoryName(String childCategoryName) {
		this.childCategoryName = childCategoryName;
	}

	public String getGrandChildCategoryId() {
		return grandChildCategoryId;
	}

	public void setGrandChildCategoryId(String grandChildCategoryId) {
		this.grandChildCategoryId = grandChildCategoryId;
	}

	public String getGrandChildCategoryName() {
		return grandChildCategoryName;
	}

	public void setGrandChildCategoryName(String grandChildCategoryName) {
		this.grandChildCategoryName = grandChildCategoryName;
	}

	@Override
	public String toString() {
		return "EditCategoryForm [parentCategoryId=" + parentCategoryId + ", parentCategoryName=" + parentCategoryName
				+ ", childCategoryId=" + childCategoryId + ", childCategoryName=" + childCategoryName
				+ ", grandChildCategoryId=" + grandChildCategoryId + ", grandChildCategoryName="
				+ grandChildCategoryName + "]";
	}

}
