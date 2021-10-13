package com.digitalBook.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/data")
//기초정보 > 종자(시료)
public class SeedController {
	
	//종자(시료) 목록
	@GetMapping(value = "seed")
	public ModelAndView getReport(ModelAndView mv) {
		
		mv.setViewName("seed/seed_list");
		
		return mv;
	}
	
	//종자(시료) 등록화면
	@GetMapping(value = "seed/insert")
	public ModelAndView getReportInsert(ModelAndView mv) {
		
		mv.setViewName("seed/seed_insert");
		
		return mv;
	}
	
}
