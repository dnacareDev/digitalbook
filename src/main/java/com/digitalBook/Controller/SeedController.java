package com.digitalBook.Controller;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
//기초정보 > 종자(시료)
public class SeedController {
	
	@Autowired
	private SeedService service;
	
	//종자(시료) 목록
	@GetMapping(value = "seed")
	public ModelAndView getReport(ModelAndView mv) {
		
		mv.setViewName("seed/seed_list");
		
		return mv;
	}
	
	//종자(시료) 등록화면
	@GetMapping(value = "seed/insert")
	public ModelAndView getReportInsert(ModelAndView mv) {
		
		List<Report> report = service.selectReportList();
		List<User> user = service.selectUserList();
		List<Eaches> eaches = service.selectEachesList();
		List<Warehouse> warehouse = service.selectWarehouseList();
		List<Division> division = service.selectDivisionList();
		
		mv.addObject("report", report);
		mv.addObject("user", user);
		mv.addObject("eaches", eaches);
		mv.addObject("warehouse", warehouse);
		mv.addObject("division", division);
		
		mv.setViewName("seed/seed_insert");
		
		return mv;
	}
	
	//시료 검색
	@ResponseBody
	@RequestMapping("/seed/searchSeed")
	public Map<String, Object> searchSeed(@RequestParam(name = "search_type", required = false) String search_type,
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam("page_num") int page_num,
			@RequestParam("limit") int limit){
		
		Map<String, Object> result = new LinkedHashMap<>();
		
		int count = service.SearchSeedCount(search_type, keyword);
		
		int offset = (page_num - 1) * limit;
		int end_page = (count + limit - 1) / limit;
		
		List<Seed> seed = service.SearchSeed(search_type, keyword, offset, limit);
		
		result.put("seed", seed);
		result.put("page_num", page_num);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
	
	//품종, 유전정보 조회
	@ResponseBody
	@RequestMapping("/seed/selectGenetic")
	public List<Genetic> selectGenetic(@RequestParam(name = "division_id", required = true) int division_id,
									@RequestParam(name = "genetic_type", required = true) int genetic_type){
		
		List<Genetic> genetic = service.selectGeneticList(division_id, genetic_type);
		
		return genetic;
	}
	
	//시료 등록
	@ResponseBody
	@RequestMapping("/seed/insertSeed")
	public int insertSeed(Seed seed) {
		
		System.out.println(seed);
		
		Calendar cal = Calendar.getInstance();
		String last_seed_code =service.selectLastSeedCode();
		String code1 = "si-";
		String code2 = String.valueOf(cal.get(Calendar.YEAR))+"-";
		
		if(last_seed_code == null || last_seed_code.equals("")) {
			seed.setSeed_code(code1+code2+"00001");
		}else {
			String[] strArr = last_seed_code.split("-");
			int code3 = Integer.parseInt(strArr[2]) + 1;
			seed.setSeed_code(code1+code2+String.format("%05d", code3));
		}
		
		int result = service.insertSeed(seed);
		
		return result;
	}
	
}
