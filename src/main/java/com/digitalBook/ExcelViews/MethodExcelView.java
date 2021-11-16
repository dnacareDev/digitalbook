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

import com.digitalBook.Entity.Method;
import com.digitalBook.Mapper.MethodMapper;

import lombok.RequiredArgsConstructor;

@Component("methodExcelView")
@RequiredArgsConstructor
public class MethodExcelView extends AbstractXlsView {
	
	private final MethodMapper mapper;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int user_group = (int) model.get("user_group");
		
		List<Method> methods = mapper.selectMethodExcelList(user_group);
		
		Sheet sheet = workbook.createSheet("재배 프로토콜");
		
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		
		//header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("Method ID");
		cell = row.createCell(1);
		cell.setCellValue("Method명");
		cell = row.createCell(2);
		cell.setCellValue("대상 작목");
		cell = row.createCell(3);
		cell.setCellValue("분류");
		cell = row.createCell(4);
		cell.setCellValue("항목");
		cell = row.createCell(5);
		cell.setCellValue("Method 설명");
		cell = row.createCell(6);
		cell.setCellValue("Step NO");
		cell = row.createCell(7);
		cell.setCellValue("상태");
		cell = row.createCell(8);
		cell.setCellValue("등록일");
		
		//body
		for(Method method : methods) {
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue(method.getMethod_code());
			cell = row.createCell(1);
			cell.setCellValue(method.getMethod_title());
			cell = row.createCell(2);
			cell.setCellValue(method.getD1_name());
			cell = row.createCell(3);
			cell.setCellValue(method.getD2_name());
			cell = row.createCell(4);
			cell.setCellValue(method.getD3_name());
			cell = row.createCell(5);
			cell.setCellValue(method.getMethod_contents());
			cell = row.createCell(6);
			cell.setCellValue(method.getStep_no());
			if(method.getMethod_status() == 0) {
				cell = row.createCell(7);
				cell.setCellValue("승인요청");
			}else if(method.getMethod_status() == 1) {
				cell = row.createCell(7);
				cell.setCellValue("수정요청");
			}else if(method.getMethod_status() == 2) {
				cell = row.createCell(7);
				cell.setCellValue("승인");
			}
			cell = row.createCell(8);
			cell.setCellValue(method.getCreate_date());
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=method.xls");

	}

}
