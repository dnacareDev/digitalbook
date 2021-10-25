package com.digitalBook.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("plan")
public class PlanController
{
	//재배계획 목록 페이지
	@RequestMapping("/list")
	public ModelAndView PlanList(ModelAndView mv)
	{
		mv.setViewName("plan/plan_list");
		
		return mv;
	}
	
	// 
	@RequestMapping("/insert")
	public ModelAndView PlanInsert(ModelAndView mv)
	{
		mv.setViewName("plan/plan_insert2");
		
		return mv;
	}
}