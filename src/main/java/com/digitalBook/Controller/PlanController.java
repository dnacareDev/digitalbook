package com.digitalBook.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Fertilizer;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Entity.User;
import com.digitalBook.Service.PlanService;

@Controller
@RequestMapping("plan")
public class PlanController
{
	
	@Autowired
	private PlanService service;
	
	//재배계획 목록 페이지
	@RequestMapping("/list")
	public ModelAndView PlanList(ModelAndView mv)
	{
		mv.setViewName("plan/plan_list");
		
		return mv;
	}
	
	//재배계획 등록 페이지
	@RequestMapping("/insert")
	public ModelAndView PlanInsert(ModelAndView mv)
	{
		
		List<Report> report = service.selectReportList();
		List<Fertilizer> fert = service.selectFertilizerList(0, 0);
		
		mv.addObject("report", report);
		mv.addObject("fert", fert);
		
		mv.setViewName("plan/plan_insert");
		
		return mv;
	}
	
	//seed list 조회
	@ResponseBody
	@RequestMapping("selectSeed")
	public List<Seed> SelectSeed(Authentication auth,
								@RequestParam("report_id") int report_id)
	{
		
		User prin = (User)auth.getPrincipal();
		
		List<Seed> seed = service.selectSeedList(prin.getUser_group(), report_id);
		
		return seed;
	}
	
	//fertilizer list 조회
	@ResponseBody
	@RequestMapping("selectFertilizer")
	public List<Fertilizer> SelectFertilizer(@RequestParam("fert_id") int fert_id)
	{
		List<Fertilizer> fert = service.selectFertilizerList(1, fert_id);
		
		return fert;
	}
	
}