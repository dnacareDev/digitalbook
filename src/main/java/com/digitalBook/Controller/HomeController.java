package com.digitalBook.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.User;

@Controller
@RequestMapping(value = "/")
public class HomeController
{
	@GetMapping("/home")
	public ModelAndView getHome(ModelAndView mv, Authentication auth)
	{
		
		User prin = (User)auth.getPrincipal();
		
		if(prin.getUser_type() == 0) {
			mv.setViewName("home/admin_home");
		}else {
			mv.setViewName("home/user_home");
		}
		
		
		return mv;
	}
	
}