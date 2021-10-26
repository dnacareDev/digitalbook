package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Report;
import com.digitalBook.Mapper.ReportMapper;
import com.digitalBook.Service.ReportService;

@Service
public class ReportServiceImpl implements ReportService
{
	@Autowired
	private ReportMapper mapper;

	// 과제 등록
	@Override
	public int InsertReport(List<Report> reports)
	{
		return mapper.InsertReport(reports);
	}
}