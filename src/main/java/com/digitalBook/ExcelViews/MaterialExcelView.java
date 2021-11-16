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

import com.digitalBook.Entity.Material;
import com.digitalBook.Mapper.MaterialMapper;

import lombok.RequiredArgsConstructor;

@Component("materialExcelView")
@RequiredArgsConstructor
public class MaterialExcelView extends AbstractXlsView {
	
	private final MaterialMapper mapper;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int user_group = (int) model.get("user_group");
		
		List<Material> materials = mapper.selectMaterialExcelList(user_group);
		
		Sheet sheet = workbook.createSheet("기타(농자재)");
		
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		
		//header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("기타 항목 ID");
		cell = row.createCell(1);
		cell.setCellValue("항목명");
		cell = row.createCell(2);
		cell.setCellValue("등록자");
		cell = row.createCell(3);
		cell.setCellValue("사용여부");
		cell = row.createCell(4);
		cell.setCellValue("등록일");
		
		//body
		for(Material material : materials) {
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue(material.getMaterial_code());
			cell = row.createCell(1);
			cell.setCellValue(material.getMaterial_name());
			cell = row.createCell(2);
			cell.setCellValue(material.getUser_name_k());
			cell = row.createCell(3);
			cell.setCellValue(material.getMaterial_status() == 0 ? "N" : "Y");
			cell = row.createCell(4);
			cell.setCellValue(material.getCreate_date());
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=material.xls");

	}

}
