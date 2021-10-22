package com.digitalBook.Controller;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Reagent;
import com.digitalBook.Entity.User;
import com.digitalBook.Service.ReagentService;

@Controller
@RequestMapping(value = "/data")
//기초정보 > 시약,농약,비료
public class ReagentController {
	
	@Autowired
	private ReagentService service;
	
	//시약,농약,비료 목록
	@GetMapping(value = "/reagent")
	public ModelAndView getReagent(ModelAndView mv) {
		
		mv.setViewName("reagent/reagent_list");
		
		return mv;
	}
	
	//시약,농약,비료 등록화면
	@GetMapping(value = "/reagent/insert")
	public ModelAndView getReagentInsert(ModelAndView mv) {
		
		List<Eaches> eaches = service.selectEaches();
		mv.addObject("eaches", eaches);
		
		mv.setViewName("reagent/reagent_insert");
		
		return mv;
	}
	
	//시약 검색
	@ResponseBody
	@RequestMapping("/reagent/searchReagent")
	public Map<String, Object> searchReagent(Authentication auth,
											@RequestParam(name = "search_type", required = false) String search_type,
											@RequestParam(name = "keyword", required = false) String keyword,
											@RequestParam("page_num") int page_num,
											@RequestParam("limit") int limit){
		
		User prin = (User)auth.getPrincipal();
		
		Map<String, Object> result = new LinkedHashMap<>();
		
		int count = service.SearchReagentCount(search_type, keyword, prin.getUser_group());
		
		int offset = (page_num - 1) * limit;
		int end_page = (count + limit - 1) / limit;
		
		List<Reagent> reagent = service.SearchReagent(search_type, keyword, offset, limit, prin.getUser_group());
		
		result.put("reagent", reagent);
		result.put("page_num", page_num);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
	
	//시약 등록
	@ResponseBody
	@RequestMapping("/reagent/insertReagent")
	public int insertReagent(Authentication auth, Reagent reagent) {
		
		User prin = (User)auth.getPrincipal();
		
		Calendar cal = Calendar.getInstance();
		
		String last_reagent_code = service.selectLastReagnetCode(prin.getUser_group());
		String code1 = "re-";
		String code2 = String.valueOf(cal.get(Calendar.YEAR))+"-";
		
		if(last_reagent_code == null || last_reagent_code.equals("")) {
			reagent.setReagent_code(code1+code2+"00001");
		}else {
			String[] strArr = last_reagent_code.split("-");
			int code3 = Integer.parseInt(strArr[2]) + 1;
			reagent.setReagent_code(code1+code2+String.format("%05d", code3));
		}
		
		reagent.setUser_id(prin.getUser_id());
		reagent.setUser_group(prin.getUser_group());
		int result = service.insertReagent(reagent);
		
		return result;
	}
	
	//시약 수정화면
	@RequestMapping("/reagent/modify")
	public ModelAndView reagentModify(ModelAndView mv, @RequestParam(name = "reagent_id", required = true) int reagent_id) {
		
		Reagent reagent = service.selectReagentDetail(reagent_id);
		List<Eaches> eaches = service.selectEaches();
		
		mv.addObject("reagent", reagent);
		mv.addObject("eaches", eaches);
		
		mv.setViewName("reagent/reagent_modify");
		
		return mv;
	}
	
	//시약 수정
	@ResponseBody
	@RequestMapping("/reagent/updateReagent")
	public int updateReagent(Reagent reagnet) {
		
		int result = service.updateReagent(reagnet);
		
		return result;
	}
	
	//시약 삭제
	@ResponseBody
	@RequestMapping("/reagent/deleteReagent")
	public int deleteReagent(@RequestParam(name = "reagent_id", required = true) int reagent_id) {
		
		int result = service.deleteReagent(reagent_id);
		
		return result;
	}

}
