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

import com.digitalBook.Entity.Seed;
import com.digitalBook.Mapper.SeedMapper;

import lombok.RequiredArgsConstructor;

@Component("seedExcelView")
@RequiredArgsConstructor
public class SeedExcelView extends AbstractXlsView {

	private final SeedMapper mapper;
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int user_group = (int) model.get("user_group");
		
		List<Seed> seeds = mapper.selectSeedExcelList(user_group);
		
		Sheet sheet = workbook.createSheet("종자(시료)");
		
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		
		//header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("과제번호");
		cell = row.createCell(1);
		cell.setCellValue("과제명");
		cell = row.createCell(2);
		cell.setCellValue("작목명");
		cell = row.createCell(3);
		cell.setCellValue("품종/유전자원명");
		cell = row.createCell(4);
		cell.setCellValue("시료수");
		cell = row.createCell(5);
		cell.setCellValue("발송인");
		cell = row.createCell(6);
		cell.setCellValue("보관장소");
		cell = row.createCell(7);
		cell.setCellValue("발송일자");
		cell = row.createCell(8);
		cell.setCellValue("수취일자");
		
		
		//body
		for(Seed seed : seeds) {
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue(seed.getReport_code());
			cell = row.createCell(1);
			cell.setCellValue(seed.getReport_title());
			cell = row.createCell(2);
			cell.setCellValue(seed.getDivision()+" 외"+(seed.getSeed_count()-1)+"개");
			cell = row.createCell(3);
			cell.setCellValue(seed.getGenetic());
			cell = row.createCell(4);
			cell.setCellValue(seed.getSeed_amount());
			cell = row.createCell(5);
			cell.setCellValue(seed.getSeed_sender());
			cell = row.createCell(6);
			cell.setCellValue(seed.getWarehouse());
			cell = row.createCell(7);
			cell.setCellValue(seed.getSend_date());
			cell = row.createCell(8);
			cell.setCellValue(seed.getReceive_date());
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=seed.xls");

	}

}
