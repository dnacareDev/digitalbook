package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Report;

public interface ReportService
{
	// 과제 등록
	int InsertReport(List<Report> reports);
}