package com.digitalBook.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/data")
//기초정보 > 과제
public class ReportController {
	
	//과제 목록
	@GetMapping(value = "report")
	public ModelAndView getReport(ModelAndView mv) {
		
		mv.setViewName("report/report_list");
		
		return mv;
	}
	
	//과제 등록화면
	@GetMapping(value = "report/insert")
	public ModelAndView getReportInsert(ModelAndView mv) {
		
		mv.setViewName("report/report_insert");
		
		return mv;
	}
	
}
