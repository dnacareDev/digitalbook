package com.digitalBook.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "template")
public class TemplateController
{
	@RequestMapping("/blank")
	public ModelAndView BlankPage(ModelAndView mv)
	{
		mv.setViewName("template/sampleBlankTemplate");
		
		return mv;
	}
	
	@RequestMapping("/result")
	public ModelAndView ResultPage(ModelAndView mv)
	{
		mv.setViewName("template/result");
		
		return mv;
	}
}