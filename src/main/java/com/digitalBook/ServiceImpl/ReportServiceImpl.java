package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.ReportFile;
import com.digitalBook.Mapper.ReportMapper;
import com.digitalBook.Service.ReportService;

@Service
public class ReportServiceImpl implements ReportService
{
	@Autowired
	private ReportMapper mapper;

	// 과제 갯수 조회
	@Override
	public int SelectReportFileCount(String search_type, String keyword)
	{
		return mapper.SelectReportFileCount(search_type, keyword);
	}

	// 과제 검색
	@Override
	public List<ReportFile> SearchReportFile(String search_type, String keyword, int offset, int limit)
	{
		return mapper.SearchReportFile(search_type, keyword, offset, limit);
	}

	// 과제 상세 조회
	@Override
	public ReportFile SelectReportDetail(int report_file_id)
	{
		return mapper.SelectReportDetail(report_file_id);
	}

	// 과제 파일 등록
	@Override
	public int InsertReportFile(ReportFile reportFile)
	{
		return mapper.InsertReportFile(reportFile);
	}
	
	// 과제 등록
	@Override
	public int InsertReport(List<Report> reports)
	{
		return mapper.InsertReport(reports);
	}

	// 과제 수정
	@Override
	public int UpdateReportFile(ReportFile report_file)
	{
		return mapper.UpdateReportFile(report_file);
	}

	// 과제 파일 삭제
	@Override
	public int DeleteReportFile(int report_file_id)
	{
		return mapper.DeleteReportFile(report_file_id);
	}

	// 과제 삭제
	@Override
	public int DeleteReport(int report_file_id)
	{
		return mapper.DeleteReport(report_file_id);
	}
	
	//과제 상태 변경
	@Override
	public int UpdateReportStatus(List<Report> reports) {
		
		return mapper.UpdateReportStatus(reports);
	}
}