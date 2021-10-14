package com.digitalBook.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Research;
import com.digitalBook.Service.MethodService;
import com.digitalBook.Service.ResearchService;

@Controller
@RequestMapping("/method")
public class MethodController
{
	@Autowired
	private MethodService service;
	
	@Autowired
	private ResearchService researchService;
	
	// 재배 프로토콜 목록 페이지
	@RequestMapping("/list")
	public ModelAndView MethodList(ModelAndView mv)
	{
		mv.setViewName("method/method_list");
		
		return mv;
	}
	
	// 재배 프로토콜 등록 페이지
	@RequestMapping("/insert")
	public ModelAndView MethodInsert(ModelAndView mv)
	{
		List<Division> division = researchService.SelectDivision(0, 0);
		
		mv.addObject("division", division);
		
		mv.setViewName("method/method_insert");
		
		return mv;
	}
	
	// 조사항목 조회
	@ResponseBody
	@RequestMapping("/selectDivision")
	public List<Division> SelectDivision(@RequestParam("division_id") int division_id, @RequestParam("division_depth") int division_depth)
	{
		List<Division> result = researchService.SelectDivision(division_id, division_depth);
		
		return result;
	}
	
	// 입력단위 조회
	@ResponseBody
	@RequestMapping("/selectEaches")
	public List<Eaches> SelectEaches(@RequestParam("eaches_type") String eaches_type)
	{
		List<Eaches> result = service.SelectEaches(eaches_type);
		
		return result;
	}
	
	// 조사방법 조회
	@ResponseBody
	@RequestMapping("/selectResearch")
	public List<Research> SelectResearch(@RequestParam("division_id") int division_id)
	{
		List<Research> result = service.SelectResearch(division_id);
		
		return result;
	}
}