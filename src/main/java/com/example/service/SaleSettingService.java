package com.example.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Sale;
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
	 * セールを中止する.
	 * 
	 * @param itemIdList 商品ID
	 */
	public void cancel(Integer[] itemIdList) {
		saleRepository.delete(itemIdList);
	}

}
