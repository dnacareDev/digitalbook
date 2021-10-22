package com.digitalBook.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("plan")
public class PlanController {
	
	//재배계획 목록 페이지
	@RequestMapping("planning")
	public ModelAndView planList(ModelAndView mv) {
		
		mv.setViewName("plan/plan_list");
		
		return mv;
	}
	
}
