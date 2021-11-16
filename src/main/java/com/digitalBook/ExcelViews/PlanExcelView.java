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

import com.digitalBook.Entity.Plan;
import com.digitalBook.Mapper.PlanMapper;

import lombok.RequiredArgsConstructor;

@Component("planExcelView")
@RequiredArgsConstructor
public class PlanExcelView extends AbstractXlsView {
	
	private final PlanMapper mapper;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int user_group = (int) model.get("user_group");
		
		List<Plan> plans = mapper.selectPlanExcelList(user_group);
		
		Sheet sheet = workbook.createSheet("계획수립");
		
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		
		//header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("생육조사 ID");
		cell = row.createCell(1);
		cell.setCellValue("과제명");
		cell = row.createCell(2);
		cell.setCellValue("포장");
		cell = row.createCell(3);
		cell.setCellValue("조사항목");
		cell = row.createCell(4);
		cell.setCellValue("반복수");
		cell = row.createCell(5);
		cell.setCellValue("처리수");
		cell = row.createCell(6);
		cell.setCellValue("상태");
		cell = row.createCell(7);
		cell.setCellValue("등록일");
		
		//body
		for(Plan plan : plans) {
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue(plan.getPlan_code());
			cell = row.createCell(1);
			cell.setCellValue(plan.getReport_title());
			cell = row.createCell(2);
			cell.setCellValue(plan.getStorage_name());
			cell = row.createCell(3);
			cell.setCellValue(plan.getMethod_title());
			cell = row.createCell(4);
			cell.setCellValue(plan.getPlan_repeat());
			cell = row.createCell(5);
			cell.setCellValue(plan.getPlan_segment());
			if(plan.getPlan_status() == 0) {
				cell = row.createCell(6);
				cell.setCellValue("승인요청");
			}else if(plan.getPlan_status() == 1) {
				cell = row.createCell(6);
				cell.setCellValue("수정요청");
			}else if(plan.getPlan_status() == 2) {
				cell = row.createCell(6);
				cell.setCellValue("승인");
			}
			cell = row.createCell(7);
			cell.setCellValue(plan.getCreate_date());
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=plan.xls");

	}

}
