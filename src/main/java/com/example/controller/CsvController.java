package com.example.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.DownloadHelper;
import com.example.ItemForDownload;
import com.example.service.ShowListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Controller
@RequestMapping("/download")
public class CsvController {

	@Autowired
	private DownloadHelper downloadHelper;

	@Autowired
	private ShowListService showListService;

	/**
	 * CsvMapperで、csvを作成する。
	 * 
	 * @return csv(String)
	 * @throws JsonProcessingException
	 */
	public String getCsvText() throws JsonProcessingException {
		CsvMapper mapper = new CsvMapper();
		// 文字列にダブルクオートをつける
		mapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true);
		// ヘッダをつける
		CsvSchema schema = mapper.schemaFor(ItemForDownload.class).withHeader();
		// 商品情報取得
//		List<Item> itemList = showListService.getAllItems();
//		List<ItemForDownload> itemListForCsv = new ArrayList<>();
//		itemList.forEach(item -> {
//			ItemForDownload itemForCsv = new ItemForDownload(item);
//			itemListForCsv.add(itemForCsv);
//		});
		List<ItemForDownload> itemListForCsv = showListService.getAllItems();
		return mapper.writer(schema).writeValueAsString(itemListForCsv);
	}

	/**
	 * 全商品情報のcsvをダウンロードする。
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/all-items")
	public ResponseEntity<byte[]> download() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		LocalDateTime now = LocalDateTime.now();
		downloadHelper.addContentDisposition(headers, "商品一覧(" + now + ").csv");
		return new ResponseEntity<>(getCsvText().getBytes("MS932"), headers, HttpStatus.OK);
	}
}