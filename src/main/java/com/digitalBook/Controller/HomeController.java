package com.digitalBook.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Plan;
import com.digitalBook.Entity.User;
import com.digitalBook.Service.HomeService;

@Controller
@RequestMapping(value = "/")
public class HomeController
{
	
	@Autowired
	private HomeService service;
	
	@GetMapping("/home")
	public ModelAndView getHome(ModelAndView mv, Authentication auth)
	{
		
		User prin = (User)auth.getPrincipal();
		
		List<Plan> delay_plan = service.selectDelayPlanList(prin.getUser_group(), prin.getUser_id());
		List<Plan> current_plan = service.selectCurrentPlanList(prin.getUser_group(), prin.getUser_id());
		List<Plan> progress_plan = service.selectProgressPlanList(prin.getUser_group(),prin.getUser_id());
		mv.addObject("delay_plan", delay_plan);
		mv.addObject("current_plan", current_plan);
		mv.addObject("progress_plan",progress_plan);
		if(prin.getUser_type() == 0) {
			mv.setViewName("home/admin_home");
		}else {
			mv.setViewName("home/user_home");
		}
		
		
		
		return mv;
	}
	
}