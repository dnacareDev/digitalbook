package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.ReportFile;

public interface ReportService
{
	// 과제 갯수 조회
	int SelectReportFileCount(String search_type, String keyword);

	// 과제 검색
	List<ReportFile> SearchReportFile(String search_type, String keyword, int offset, int limit);

	// 과제 상세 조회 
	ReportFile SelectReportDetail(int report_file_id);

	// 과제 파일 등록
	int InsertReportFile(ReportFile reportFile);
	
	// 과제 등록
	int InsertReport(List<Report> reports);

	// 과제 수정
	int UpdateReportFile(ReportFile report_file);

	// 과제 파일 삭제
	int DeleteReportFile(int report_file_id);

	// 과제 삭제
	int DeleteReport(int report_file_id);
	
	//과제 상태 변경
	int UpdateReportStatus(List<Report> reports);
}