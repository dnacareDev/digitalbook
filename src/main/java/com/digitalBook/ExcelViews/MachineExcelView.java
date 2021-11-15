package com.digitalBook.ExcelViews;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.digitalBook.Entity.Machine;
import com.digitalBook.Mapper.MachineMapper;

import lombok.RequiredArgsConstructor;

@Component("machineExcelView")
@RequiredArgsConstructor
public class MachineExcelView extends AbstractXlsView {
	
	private final MachineMapper mapper;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int user_group = (int) model.get("user_group");
		
		List<Machine> machines = mapper.selectMachineExcelList(user_group);
		
		Sheet sheet = workbook.createSheet("장비");
		
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		
		//셀 스타일 통화로 설정
		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("#,##0"));
		
		//header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("장비 ID");
		cell = row.createCell(1);
		cell.setCellValue("장비명");
		cell = row.createCell(2);
		cell.setCellValue("모델명");
		cell = row.createCell(3);
		cell.setCellValue("취득일자");
		cell = row.createCell(4);
		cell.setCellValue("취득금액");
		cell = row.createCell(5);
		cell.setCellValue("장비용도");
		cell = row.createCell(6);
		cell.setCellValue("등록자");
		cell = row.createCell(7);
		cell.setCellValue("사용여부");
		cell = row.createCell(8);
		cell.setCellValue("등록일");
		
		//body
		for(Machine machine : machines) {
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue(machine.getMachine_code());
			cell = row.createCell(1);
			cell.setCellValue(machine.getMachine_name());
			cell = row.createCell(2);
			cell.setCellValue(machine.getMachine_model());
			cell = row.createCell(3);
			cell.setCellValue(machine.getMachine_date());
			cell = row.createCell(4);
			cell.setCellValue(machine.getMachine_price());
			cell.setCellStyle(style);
			cell = row.createCell(5);
			cell.setCellValue(machine.getMachine_purpose());
			cell = row.createCell(6);
			cell.setCellValue(machine.getUser_name_k());
			cell = row.createCell(7);
			cell.setCellValue(machine.getMachine_status() == 0 ? "N" : "Y");
			cell = row.createCell(8);
			cell.setCellValue(machine.getCreate_date());
			
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=machine.xls");

	}

}
