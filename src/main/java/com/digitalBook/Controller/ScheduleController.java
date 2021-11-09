package com.digitalBook.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Schedule;
import com.digitalBook.Entity.User;
import com.digitalBook.Service.ScheduleService;

@Controller
@RequestMapping("/schedule")
public class ScheduleController
{
	@Autowired
	private ScheduleService service;
	
	@RequestMapping("month")
	public ModelAndView Month(ModelAndView mv, Authentication auth)
	{
		User prin = (User)auth.getPrincipal();
		
		if(prin.getUser_type() == 0)
		{
			mv.setViewName("schedule/admin_month");
		}
		else
		{
			mv.setViewName("schedule/user_month");
		}
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("selectUserSchedule")
	public List<Schedule> SelectUserSchedule(Authentication auth)
	{
		User prin = (User)auth.getPrincipal();
		
		List<Schedule> result = service.SelectUserSchedule(prin.getUser_id());
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("selectAdminSchedule")
	public List<Schedule> SelectAdminSchedule(Authentication auth)
	{
		User prin = (User)auth.getPrincipal();
		
		List<Schedule> result = service.SelectAdminSchedule(prin);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("selectScheduleDetail")
	public Schedule SelectScheduleDetail(@RequestParam("sch_id") int sch_id)
	{
		Schedule result = service.SelectScheduleDetail(sch_id);
		
		return result;
	}
}