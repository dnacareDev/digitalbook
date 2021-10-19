package com.digitalBook.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//기초정보 > 사용자
@Controller
@RequestMapping(value = "/data")
public class UserController
{
	//사용자 목록
	@RequestMapping(value = "user")
	public ModelAndView UserList(ModelAndView mv)
	{
		mv.setViewName("user/user_list");
		
		return mv;
	}
}