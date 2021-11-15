package com.digitalBook.ExcelViews;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.digitalBook.Entity.Reagent;
import com.digitalBook.Mapper.ReagentMapper;

import lombok.RequiredArgsConstructor;

@Component("reagentExcelView")
@RequiredArgsConstructor
public class ReagentExcelView extends AbstractXlsView {
	
	private final ReagentMapper mapper;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int user_group = (int) model.get("user_group");
		
		List<Reagent> reagents = mapper.selectReagentExcelList(user_group);
		
		Sheet sheet = workbook.createSheet("조사방법");
		
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		
		//header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("시약•농약•비료 ID");
		cell = row.createCell(1);
		cell.setCellValue("종류");
		cell = row.createCell(2);
		cell.setCellValue("품명");
		cell = row.createCell(3);
		cell.setCellValue("규격");
		cell = row.createCell(4);
		cell.setCellValue("영문별칭");
		cell = row.createCell(5);
		cell.setCellValue("국문별칭");
		cell = row.createCell(6);
		cell.setCellValue("제조사");
		cell = row.createCell(7);
		cell.setCellValue("수량");
		cell = row.createCell(8);
		cell.setCellValue("등록자");
		cell = row.createCell(9);
		cell.setCellValue("사용여부");
		cell = row.createCell(10);
		cell.setCellValue("등록일");
		
		//body
		for(Reagent reagent : reagents) {
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue(reagent.getReagent_code());
			cell = row.createCell(1);
			cell.setCellValue(reagent.getReagent_type());
			cell = row.createCell(2);
			cell.setCellValue(reagent.getReagent_name());
			cell = row.createCell(3);
			cell.setCellValue(reagent.getReagent_standard());
			cell = row.createCell(4);
			cell.setCellValue(reagent.getReagent_nick_e());
			cell = row.createCell(5);
			cell.setCellValue(reagent.getReagent_nick_k());
			cell = row.createCell(6);
			cell.setCellValue(reagent.getReagent_manufacturer());
			cell = row.createCell(7);
			cell.setCellValue(reagent.getReagent_quantity());
			cell = row.createCell(8);
			cell.setCellValue(reagent.getUser_name_k());
			cell = row.createCell(9);
			cell.setCellValue(reagent.getReagent_status() == 0 ? "N" : "Y");
			cell = row.createCell(10);
			cell.setCellValue(reagent.getCreate_date());
			
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=reagent.xls");

	}

}
