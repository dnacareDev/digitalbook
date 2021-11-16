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

import com.digitalBook.Entity.ReportFile;
import com.digitalBook.Mapper.ReportMapper;

import lombok.RequiredArgsConstructor;

@Component("reportExcelView")
@RequiredArgsConstructor
public class ReportExcelView extends AbstractXlsView {
	
	private final ReportMapper mapper;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<ReportFile> reportFiles = mapper.selectReportExcelList();
		
		Sheet sheet = workbook.createSheet("과제");
		
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		
		//header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("년도");
		cell = row.createCell(1);
		cell.setCellValue("설명");
		cell = row.createCell(2);
		cell.setCellValue("등록자");
		cell = row.createCell(3);
		cell.setCellValue("등록일");
		
		//body
		for(ReportFile report : reportFiles) {
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue(report.getReport_year());
			cell = row.createCell(1);
			cell.setCellValue(report.getReport_contents());
			cell = row.createCell(2);
			cell.setCellValue(report.getUser_name_k());
			cell = row.createCell(3);
			cell.setCellValue(report.getCreate_date());
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=report.xls");

	}

}
