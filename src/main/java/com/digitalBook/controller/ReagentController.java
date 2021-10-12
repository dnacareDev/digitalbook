package com.digitalBook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/data")
//기초정보 > 시약,농약,비료
public class ReagentController {
	
	//시약,농약,비료 목록
	@GetMapping(value = "/reagent")
	public ModelAndView getReagent(ModelAndView mv) {
		
		mv.setViewName("reagent/reagentList");
		
		return mv;
	}
	
	//시약,농약,비료 등록화면
	@GetMapping(value = "/reagent/insert")
	public ModelAndView getReagentInsert(ModelAndView mv) {
		
		mv.setViewName("reagent/reagentInsert");
		
		return mv;
	}

}
