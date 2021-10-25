package com.digitalBook.Controller;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Genetic;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Entity.User;
import com.digitalBook.Entity.Warehouse;
import com.digitalBook.Service.SeedService;

@Controller
@RequestMapping(value = "/data")
// 기초정보 > 종자(시료)
public class SeedController
{
	@Autowired
	private SeedService service;
	
	// 종자(시료) 목록
	@GetMapping(value = "seed")
	public ModelAndView Report(ModelAndView mv)
	{
		mv.setViewName("seed/seed_list");
		
		return mv;
	}
	
	// 종자(시료) 등록화면
	@GetMapping(value = "seed/insert")
	public ModelAndView ReportInsert(ModelAndView mv, Authentication auth)
	{
		User prin = (User)auth.getPrincipal();
		
		List<Report> report = service.SelectReportList(prin.getUser_group());								// 과제 list
		List<User> user = service.SelectUserList(prin.getUser_group());					// 사용자 list
		List<Eaches> eaches = service.SelectEachesList();								// 단위 list
		List<Warehouse> warehouse = service.SelectWarehouseList();						// 저장장소 list
		List<Division> division = service.SelectDivisionList();							// 품종, 유전자원명 list
		
		mv.addObject("report", report);
		mv.addObject("user", user);
		mv.addObject("eaches", eaches);
		mv.addObject("warehouse", warehouse);
		mv.addObject("division", division);
		
		mv.setViewName("seed/seed_insert");
		
		return mv;
	}
	
	// 시료 검색
	@ResponseBody
	@RequestMapping("/seed/searchSeed")
	public Map<String, Object> SearchSeed(Authentication auth, @RequestParam(name = "search_type", required = false) String search_type, @RequestParam(name = "keyword", required = false) String keyword, @RequestParam("page_num") int page_num, @RequestParam("limit") int limit)
	{
		User prin = (User)auth.getPrincipal();
		
		Map<String, Object> result = new LinkedHashMap<>();
		
		int count = service.SearchSeedCount(search_type, keyword, prin.getUser_group());
		
		int offset = (page_num - 1) * limit;
		int end_page = (count + limit - 1) / limit;
		
		List<Seed> seed = service.SearchSeed(search_type, keyword, prin.getUser_group(), offset, limit);
		
		result.put("seed", seed);
		result.put("page_num", page_num);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
	
	// 품종, 유전정보 조회
	@ResponseBody
	@RequestMapping("/seed/selectGenetic")
	public List<Genetic> SelectGenetic(@RequestParam(name = "division_id", required = true) int division_id, @RequestParam(name = "genetic_type", required = true) int genetic_type)
	{
		List<Genetic> genetic = service.SelectGeneticList(division_id, genetic_type);
		
		return genetic;
	}
	
	// 시료 등록
	@ResponseBody
	@RequestMapping("/seed/insertSeed")
	public int[] InsertSeed(Authentication auth, @RequestBody List<Seed> seeds)
	{
		
		User prin = (User)auth.getPrincipal();
		
		int seeds_size = seeds.size();
		int result[] = new int[seeds_size];
		
		Calendar cal = Calendar.getInstance();
		
		String code1 = "si-";
		String code2 = String.valueOf(cal.get(Calendar.YEAR))+"-";
		
		int code3 = 0;
		
		String last_seed_code =service.SelectLastSeedCode(prin.getUser_group());
		String[] strArr = {};
		
		if(last_seed_code != null)
		{
			strArr = last_seed_code.split("-");
			code3 = Integer.parseInt(strArr[2]);
		}
		
		for(int i = 0; i < seeds.size(); i++)
		{
			code3++;
			
			seeds.get(i).setSeed_code(code1+code2+String.format("%05d", code3));
			seeds.get(i).setUser_group(prin.getUser_group());
			result[i] = service.InsertSeed(seeds.get(i));
		}
		
		return result;
	}
	
	// 시료 수정 화면
	@RequestMapping("/seed/modify")
	public ModelAndView SeedModify(ModelAndView mv, Authentication auth, @RequestParam("report_id") int report_id)
	{
		User prin = (User)auth.getPrincipal();
		
		List<Seed> seeds = service.SelectSeedDetailList(report_id);				// 종자(시료) list
		
		List<Report> reports = service.SelectReportList(prin.getUser_group());						// 과제 list
		List<User> user = service.SelectUserList(prin.getUser_group());			// 사용자 list
		List<Eaches> eaches = service.SelectEachesList();						// 단위 list
		List<Warehouse> warehouse = service.SelectWarehouseList();				// 저장장소 list
		List<Division> division = service.SelectDivisionList();					// 품종, 유전자원명 list
		
		mv.addObject("seeds", seeds);
		mv.addObject("report", reports);
		mv.addObject("user", user);
		mv.addObject("eaches", eaches);
		mv.addObject("warehouse", warehouse);
		mv.addObject("division", division);
		
		mv.setViewName("seed/seed_modify");
		
		return mv;
	}
	
	// 시료 수정
	@ResponseBody
	@RequestMapping("/seed/updateSeed")
	public int UpdateSeed(Authentication auth, @RequestBody List<Seed> seeds, @RequestParam(name = "cancelArr") List<Integer> cancel)
	{
		
		User prin = (User)auth.getPrincipal();
		
		int updateResult[] = new int[seeds.size()];
		int deleteResult[] = new int[cancel.size()];
		int result = 0;
		
		Calendar cal = Calendar.getInstance();
		
		String code1 = "si-";
		String code2 = String.valueOf(cal.get(Calendar.YEAR))+"-";
		
		int code3 = 0;
		
		String last_seed_code =service.SelectLastSeedCode(prin.getUser_group());
		String[] strArr = {};
		
		if(last_seed_code != null)
		{
			strArr = last_seed_code.split("-");
			
			code3 = Integer.parseInt(strArr[2]);
		}
		
		// 시료 수정 및 등록
		for(int i = 0; i < seeds.size(); i++)
		{
			int seed_id = seeds.get(i).getSeed_id();
			
			Optional<Seed> exist = Optional.ofNullable(service.SelectSeedDetail(seed_id));
			
			if(exist.isPresent())
			{
				// 해당 시료가 있으면 update
				updateResult[i] = service.UpdateSeed(seeds.get(i));
			}
			else
			{
				// 해당 시료가 없으면 insert
				code3++;
				
				seeds.get(i).setSeed_code(code1+code2+String.format("%05d", code3));
				seeds.get(i).setUser_group(prin.getUser_group());
				
				updateResult[i] = service.InsertSeed(seeds.get(i));
			}
		}
		
		// 시료 삭제
		if(cancel.isEmpty() == false)
		{
			for(int i = 0; i < cancel.size(); i++)
			{
				deleteResult[i] = service.DeleteSeed(cancel.get(i));
			}
		}
		
		Boolean isUpdate = IntStream.of(updateResult).noneMatch(x -> x == 0);
		Boolean isDelete = IntStream.of(deleteResult).noneMatch(x -> x == 0);
		
		if(isUpdate && isDelete)
		{
			result = 1;
		}
		else
		{
			result = 0;
		}
		
		return result;
	}
	
	// 시료 삭제
	@ResponseBody
	@RequestMapping("/seed/deleteSeed")
	public int DeleteSeed(@RequestBody List<Integer> seed_ids)
	{
		int result = 0;
		
		int deleteResult[] = new int[seed_ids.size()];
		
		for(int i = 0; i < seed_ids.size(); i++)
		{
			deleteResult[i] = service.DeleteSeed(seed_ids.get(i));
		}
		
		Boolean isDelete = IntStream.of(deleteResult).noneMatch(x -> x == 0);
		
		if(isDelete)
		{
			result = 1;
		}
		else
		{
			result = 0;
		}
		
		return result;
	}
}