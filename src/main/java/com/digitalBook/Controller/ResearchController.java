package com.digitalBook.Controller;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Research;
import com.digitalBook.Entity.User;
import com.digitalBook.Service.ResearchService;

@Controller
@RequestMapping("/data")
//기초정보 > 조사방법 
public class ResearchController
{
	@Autowired
	private ResearchService service;
	
	// 조사방법 목록
	@RequestMapping("/research")
	public ModelAndView Research(ModelAndView mv)
	{
		mv.setViewName("research/research_list");
		
		return mv;
	}
	
	// 조사방법 등록 페이지
	@RequestMapping("/research/insert")
	public ModelAndView ResearchInsert(ModelAndView mv)
	{
		List<Division> division = service.SelectDivision(0, 0);

		mv.addObject("division", division);
		
		mv.setViewName("research/research_insert");
		
		return mv;
	}
	
	// 조사방법 수정 페이지
	@RequestMapping("/research/modify")
	public ModelAndView ResearchModify(ModelAndView mv, @RequestParam("research_id") int research_id)
	{
		Research research = service.SelectResearchDetail(research_id);
		
		List<Division> d1 = service.SelectDivision(0, 0);
		List<Division> d2 = service.SelectDivision(research.getD1_id(), 1);
		List<Division> d3 = service.SelectDivision(research.getD2_id(), 2);
		
		mv.addObject("research", research);
		mv.addObject("d1", d1);
		mv.addObject("d2", d2);
		mv.addObject("d3", d3);
		
		mv.setViewName("research/research_modify");
		
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
	
	// 조사방법 검색
	@ResponseBody
	@RequestMapping("/research/searchResearch")
	public Map<String, Object> SearchResearch(Authentication auth, @RequestParam("search_type") int search_type, @RequestParam("keyword") String keyword, @RequestParam("page_num") int page_num, @RequestParam("limit") int limit)
	{
		User prin = (User)auth.getPrincipal();
		
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		int count = service.SearchResearchCount(search_type, keyword, prin.getUser_group());
		
		int offset = (page_num - 1) * limit;
		int start_page = ((page_num - 1) / 10) * 10 + 1;
		int end_page = (count + limit - 1) / limit;
		
		List<Research> research = service.SearchResearch(search_type, keyword, offset, limit, prin.getUser_group());
		
		result.put("research", research);
		result.put("page_num", page_num);
		result.put("start_page", start_page);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
	
	// 조사방법 등록
	@ResponseBody
	@RequestMapping("/research/insertResearch")
	public int InsertResearch(Authentication auth, @RequestParam("division_id") int division_id, @RequestParam("research_contents") String research_contents)
	{
		User prin = (User)auth.getPrincipal();
		
		Research research = new Research();
		Research last_research = service.SelectLastResearch(prin.getUser_group());
		
		Calendar cal = Calendar.getInstance();
		
		int now = cal.get(Calendar.YEAR);
		
		if(last_research == null)
		{
			research.setResearch_code("ac-" + now + "-00001");
			research.setDivision_id(division_id);
			research.setResearch_contents(research_contents);
		}
		else
		{
			String[] strArr = last_research.getResearch_code().split("-");
			
			int year = Integer.parseInt(strArr[1]);
			
			if(year == now)
			{
				int code = Integer.parseInt(strArr[2]) + 1;
				research.setResearch_code("ac-" + now + "-" + String.format("%05d", code));
			}
			else
			{
				research.setResearch_code("ac-" + now + "-00001");
			}
			
			research.setDivision_id(division_id);
			research.setResearch_contents(research_contents);
		}
		
		research.setUser_group(prin.getUser_group());
		int result = service.InsertResearch(research);
		
		return result;
	}
	
	// 조사방법 수정
	@ResponseBody
	@RequestMapping("/research/updateResearch")
	public int UpdateResearch(@RequestParam("research_id") int research_id, @RequestParam("division_id") int division_id, @RequestParam("research_contents") String research_contents)
	{
		int result = service.UpdateResearch(research_id, division_id, research_contents);
		
		return result;
	}
	
	// 조사방법 삭제
	@ResponseBody
	@RequestMapping("/research/deleteResearch")
	public int DeleteResearch(@RequestParam("research_id") int research_id)
	{
		int result = service.DeleteResearch(research_id);
		
		return result;
	}
	
	//엑셀 다운로드
	@RequestMapping(value = "/research/exceldownload", produces = "application/vnd.ms-excel")
	public String ResearchExceldownload(Authentication auth, Model model) 
	{
		
		User prin = (User)auth.getPrincipal();
		model.addAttribute("user_group", prin.getUser_group());
		
		return "researchExcelView";
	}
	
	
}