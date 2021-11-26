package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.ReportFile;

@Mapper
public interface ReportMapper
{
	// 과제 갯수 조회
	int SelectReportFileCount(@Param("search_type") String search_type, @Param("keyword") String keyword);

	// 과제 검색
	List<ReportFile> SearchReportFile(@Param("search_type") String search_type, @Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

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
	
	//엑셀 다운로드할 과제 list
	List<ReportFile> selectReportExcelList();
	
	//과제 상태 변경
	int UpdateReportStatus(@Param("reports") List<Report> reports);
}