package com.digitalBook.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/data")
// 기초정보 > 실험장소
public class StrotageController
{
	// 실험장소 목록
	@RequestMapping(value = "/storage")
	public ModelAndView getStorage(ModelAndView mv)
	{
		mv.setViewName("storage/storage_list");
		
		return mv;
	}
}