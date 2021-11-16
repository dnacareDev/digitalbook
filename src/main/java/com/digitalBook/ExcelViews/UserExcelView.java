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

import com.digitalBook.Entity.User;
import com.digitalBook.Mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Component("userExcelView")
@RequiredArgsConstructor
public class UserExcelView extends AbstractXlsView {
	
	private final UserMapper mapper;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<User> users = mapper.selectUserExcelList();
		
		Sheet sheet = workbook.createSheet("사용자");
		
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		
		//header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("사용자 ID");
		cell = row.createCell(1);
		cell.setCellValue("성명");
		cell = row.createCell(2);
		cell.setCellValue("소속실");
		cell = row.createCell(3);
		cell.setCellValue("직종");
		cell = row.createCell(4);
		cell.setCellValue("직급");
		cell = row.createCell(5);
		cell.setCellValue("권한");
		cell = row.createCell(6);
		cell.setCellValue("사용여부");
		cell = row.createCell(7);
		cell.setCellValue("등록일");
		
		//body
		for(User user : users) {
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue(user.getUser_username());
			cell = row.createCell(1);
			cell.setCellValue(user.getUser_name_k());
			cell = row.createCell(2);
			cell.setCellValue(user.getD4_department_name() == null ? "-" : user.getD4_department_name());
			cell = row.createCell(3);
			cell.setCellValue(user.getUser_position());
			cell = row.createCell(4);
			cell.setCellValue(user.getUser_level());
			if(user.getUser_type() == 0) {
				cell = row.createCell(5);
				cell.setCellValue("관리자");
			}else if(user.getUser_type() == 1) {
				cell = row.createCell(5);
				cell.setCellValue("운영자");
			}else if(user.getUser_type() == 2) {
				cell = row.createCell(5);
				cell.setCellValue("연구직");
			}else if(user.getUser_type() == 3) {
				cell = row.createCell(5);
				cell.setCellValue("공무직");
			}
			cell = row.createCell(6);
			cell.setCellValue(user.getUser_status() == 0 ? "N" : "Y");
			cell = row.createCell(7);
			cell.setCellValue(user.getCreate_date());
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=user.xls");

	}

}
