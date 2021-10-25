package com.digitalBook.Controller;

import java.io.FileReader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/data")
// 기초정보 > 과제
public class ReportController
{
	// 과제 목록
	@RequestMapping("report")
	public ModelAndView getReport(ModelAndView mv)
	{
		mv.setViewName("report/report_list");
		
		return mv;
	}
	
	// 과제 등록 페이지
	@RequestMapping("report/insert")
	public ModelAndView getReportInsert(ModelAndView mv)
	{
		mv.setViewName("report/report_insert");
		
		return mv;
	}
	
	// 과제 등록
	@ResponseBody
	@RequestMapping("report/insertReport")
	public int InsertReport(@RequestParam("report_year") int report_year, @RequestParam("report_contents") String report_contents, @RequestParam("file") MultipartFile file)
	{
		System.out.println(report_year);
		System.out.println(report_contents);
		System.out.println(file);
		
		return 0;
	}
}