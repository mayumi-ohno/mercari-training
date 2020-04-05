package com.example.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowListService;

@Controller
@RequestMapping("/download")
public class CsvController {

	/** 1回のDBアクセスで取得するデータ数 */
	private static final Integer DATAS_PER_1SQL = 1000;

	@Autowired
	private ShowListService showListService;

	@RequestMapping("/all-items")
	public void csvdownload(HttpServletResponse response) {
		LocalDateTime now = LocalDateTime.now();
		response.addHeader("Content-Disposition", "attachment; filename=\"all_items(" + now + ").csv\"");
		response.setContentType("text/csv; charset=utf-8");

		// 全データを取得するための、DBアクセス回数を求める
		Integer totalItems = showListService.getAmountOfAllItems();
		Integer totalAccess = 0;
		if (totalItems % DATAS_PER_1SQL == 0) {
			totalAccess = totalItems / DATAS_PER_1SQL;
		} else {
			totalAccess = (totalItems / DATAS_PER_1SQL) + 1;
		}

		try (ServletOutputStream out = response.getOutputStream()) {
			String header = "id,name,condition,category,brand,price,description\r\n";
			out.write(header.getBytes("UTF-8"));

			for (int i = 0; i < totalAccess; i++) {
				List<Item> itemList = showListService.getItemsForCsv(DATAS_PER_1SQL, i * DATAS_PER_1SQL);
				itemList.forEach(item -> {
					String id = String.valueOf(item.getId());
					String name = item.getName();
					String condition = String.valueOf(item.getCondition());
					String category = item.getParentCategory() + "/" + item.getChildCategory() + "/"
							+ item.getGrandChildCategory();
					String brand = item.getBrand();
					if ("null".equals(brand)) {
						brand = "";
					}
					String price = String.valueOf(item.getPrice());
					String description = item.getDescription();
					String data = id + "," + name + "," + condition + "," + category + "," + brand + "," + price + ","
							+ description + "\r\n";
					try {
						out.write(data.getBytes("UTF-8"));
					} catch (IOException e) {
					}
				});
			}
			out.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}

}