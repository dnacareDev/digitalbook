package com.digitalBook.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/data")
//기초정보 > 사용자
public class UserController {
	
	//사용자 목록
	@GetMapping(value = "user")
	public ModelAndView getUser(ModelAndView mv) {
		
		mv.setViewName("user/userList");
		
		return mv;
	}
	
}
