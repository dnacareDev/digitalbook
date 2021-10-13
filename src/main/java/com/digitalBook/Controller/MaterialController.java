package com.digitalBook.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/data")
//기초정보 > 기타(농자재)
public class MaterialController {
	
	//기타(농자재) 목록
	@GetMapping(value = "/material")
	public ModelAndView getMaterial(ModelAndView mv) {
		
		mv.setViewName("material/materialList");
		
		return mv;
	}
	
	//기타(농자재) 등록화면
	@GetMapping(value = "/material/insert")
	public ModelAndView getMaterialInsert(ModelAndView mv) {
		
		mv.setViewName("material/materialInsert");
		
		return mv;
	}
	
}
