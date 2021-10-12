package com.digitalBook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "template")
public class TemplateController {
	
	@GetMapping("/blank")
	public ModelAndView getBlankPage(ModelAndView mv) {
		
		mv.setViewName("template/sampleBlankTemplate");
		
		return mv;
	}
	
}
