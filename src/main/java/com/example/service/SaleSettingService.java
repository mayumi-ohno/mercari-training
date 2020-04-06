package com.example.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Sale;
import com.example.form.DiscountSearchingForm;
import com.example.form.SaleForm;
import com.example.repository.SaleRepository;

/**
 * セール設定を行うサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class SaleSettingService {

	@Autowired
	private SaleRepository saleRepository;

	/**
	 * セールを設定する.
	 * 
	 * @param form セール情報
	 */
	public void setting(SaleForm form) {
		List<Sale> saleList = new ArrayList<>();
		for (Integer itemId : form.getItemIdList()) {
			Sale sale = new Sale();
			sale.setItemId(itemId);
			sale.setStart(Date.valueOf(form.getStart()));
			sale.setEnd(Date.valueOf(form.getEnd()));
			sale.setDiscountRate(Integer.parseInt(form.getDiscountRate()));
			saleList.add(sale);
		}
		saleRepository.insert(saleList);
	}

	/**
	 * 検索条件に合致する商品をセールにする.
	 * 
	 * @param form セール情報
	 */
	public void discountMatchingSearch(DiscountSearchingForm form) {
		Sale sale = copyPropertiesSearchFromFormToSale(form);
		saleRepository.insertWhenSearching(sale);
	}

	/**
	 * セールを中止する.
	 * 
	 * @param itemIdList 商品ID
	 */
	public void cancel(Integer[] itemIdList) {
		saleRepository.delete(itemIdList);
	}

	/**
	 * フォームオブジェクトからドメインオブジェクトへ値コピーする.
	 * 
	 * @param form フォーム
	 * @return ドメイン
	 */
	public Sale copyPropertiesSearchFromFormToSale(DiscountSearchingForm form) {
		Sale sale = new Sale();
		sale.setStart(Date.valueOf(form.getStart()));
		sale.setEnd(Date.valueOf(form.getEnd()));
		sale.setDiscountRate(Integer.parseInt(form.getDiscountRate()));
		sale.setName(form.getName());
		try {
			sale.setParentCategoryId(Integer.parseInt(form.getParentCategoryId()));
		} catch (NumberFormatException e) {
			sale.setParentCategoryId(null);
		}
		try {
			sale.setChildCategoryId(Integer.parseInt(form.getChildCategoryId()));
		} catch (NumberFormatException e) {
			sale.setChildCategoryId(null);
		}
		try {
			sale.setGrandChildCategoryId(Integer.parseInt(form.getGrandChildCategoryId()));
		} catch (NumberFormatException e) {
			sale.setGrandChildCategoryId(null);
		}
		sale.setBrand(form.getBrand());
		return sale;
	}
}
