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

import com.digitalBook.Entity.Research;
import com.digitalBook.Mapper.ResearchMapper;

import lombok.RequiredArgsConstructor;

@Component("researchExcelView")
@RequiredArgsConstructor
public class ResearchExcelView extends AbstractXlsView {
	
	private final ResearchMapper mapper;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int user_group = (int) model.get("user_group");
		
		List<Research> researchs = mapper.selectResearchExcelList(user_group);
		
		Sheet sheet = workbook.createSheet("조사방법");
		
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		
		//header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("조사방법 ID");
		cell = row.createCell(1);
		cell.setCellValue("조사방법");
		cell = row.createCell(2);
		cell.setCellValue("작목");
		cell = row.createCell(3);
		cell.setCellValue("분류");
		cell = row.createCell(4);
		cell.setCellValue("항목");
		cell = row.createCell(5);
		cell.setCellValue("등록일");
		
		//body
		for(Research research : researchs) {
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue(research.getResearch_code());
			cell = row.createCell(1);
			cell.setCellValue(research.getResearch_contents());
			cell = row.createCell(2);
			cell.setCellValue(research.getD1());
			cell = row.createCell(3);
			cell.setCellValue(research.getD2());
			cell = row.createCell(4);
			cell.setCellValue(research.getD3());
			cell = row.createCell(5);
			cell.setCellValue(research.getCreate_date());
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=research.xls");
		
	}
	
	

}
