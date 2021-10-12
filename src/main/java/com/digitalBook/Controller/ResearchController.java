package com.digitalBook.Controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Research;
import com.digitalBook.Service.ResearchService;

@Controller
@RequestMapping(value = "/data")
//기초정보 > 조사방법 
public class ResearchController
{
	@Autowired
	private ResearchService service;
	
	//조사방법 목록
	@RequestMapping(value = "/research")
	public ModelAndView Research(ModelAndView mv)
	{
		mv.setViewName("research/research_list");
		
		return mv;
	}
	
	//조사방법 등록 페이지
	@RequestMapping(value = "/research/insert")
	public ModelAndView ResearchInsert(ModelAndView mv)
	{
		List<Division> division = service.SelectDivision(0, 0);

		mv.addObject("division", division);
		
		mv.setViewName("research/research_insert");
		
		return mv;
	}
	
	// 조사항목 조회
	@ResponseBody
	@RequestMapping("/research/selectDivision")
	public List<Division> SelectDivision(@RequestParam("division_id") int division_id, @RequestParam("division_depth") int division_depth)
	{
		List<Division> result = service.SelectDivision(division_id, division_depth);
		
		return result;
	}
	
	// 조사방법 등록
	@ResponseBody
	@RequestMapping("/research/insertResearch")
	public int InsertResearch(@RequestParam("division_id") int division_id, @RequestParam("research_contents") String research_contents)
	{
		Research research = new Research();
		Research last_research = service.SelectLastResearch();
		
		if(last_research == null)
		{
			research.setResearch_code("ac-o-002-00001");
			research.setDivision_id(division_id);
			research.setResearch_contents(research_contents);
		}
		else
		{
			String[] strArr = last_research.getResearch_code().split("-");
			
			int code = Integer.parseInt(strArr[3]) + 1;
			research.setResearch_code("ac-o-002-" + String.format("%05d", code));
			research.setDivision_id(division_id);
			research.setResearch_contents(research_contents);
		}
		
		int result = service.InsertResearch(research);
		
		return result;
	}
	
	// 조사방법 검색
	@ResponseBody
	@RequestMapping("/research/searchResearch")
	public Map<String, Object> SearchResearch(@RequestParam("search_type") int search_type, @RequestParam("keyword") String keyword, @RequestParam("page_num") int page_num, @RequestParam("limit") int limit)
	{
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		int count = service.SearchResearchCount(search_type, keyword);
		
		limit = 2;
		int offset = (page_num - 1) * limit;
		int end_page = (count + limit - 1) / limit;
		
		List<Research> research = service.SearchResearch(search_type, keyword, offset, limit);
		
		result.put("research", research);
		result.put("page_num", page_num);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
}