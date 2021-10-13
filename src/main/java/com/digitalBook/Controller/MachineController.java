package com.digitalBook.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/data")
//기초정보 > 장비
public class MachineController {
	
	//장비 목록
	@GetMapping(value = "/machine")
	public ModelAndView getMachine(ModelAndView mv) {
		
		mv.setViewName("machine/machine_list");
		
		return mv;
	}
	
	//장비 등록화면
	@GetMapping(value = "/machine/insert")
	public ModelAndView getMachineInsert(ModelAndView mv) {
		
		mv.setViewName("machine/machine_insert");
		
		return mv;
	}
	
}
