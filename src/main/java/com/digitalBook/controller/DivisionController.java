package com.digitalBook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/data")
//기초정보 > 조사방법
public class DivisionController {
	
	//조사방법 목록
	@GetMapping(value = "/division")
	public ModelAndView getDivision(ModelAndView mv) {
		
		mv.setViewName("divison/divisionList");
		
		return mv;
	}
	
	//조사방법 등록화면
	@GetMapping(value = "/division/insert")
	public ModelAndView getDivisionInsert(ModelAndView mv) {
		
		mv.setViewName("divison/divisionInsert");
		
		return mv;
	}
	
}
