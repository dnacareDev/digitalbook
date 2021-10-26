package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalBook.Entity.Report;

@Mapper
public interface ReportMapper
{
	// 과제 등록
	int InsertReport(List<Report> reports);
}