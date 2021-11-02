package com.digitalBook.Controller;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Method;
import com.digitalBook.Entity.Record;
import com.digitalBook.Entity.Research;
import com.digitalBook.Entity.Step;
import com.digitalBook.Entity.User;
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
	
	// 재배 프로토콜 검색
	@ResponseBody
	@RequestMapping("searchMethod")
	public Map<String, Object> SearchMethod(Authentication auth,
											@RequestParam(name = "search_type", required = false) String search_type,
											@RequestParam(name = "keyword", required = false) String keyword,
											@RequestParam("page_num") int page_num,
											@RequestParam("limit") int limit)
	{
		
		User prin = (User)auth.getPrincipal();
		
		Map<String, Object> result = new LinkedHashMap<>();
		
		int count = service.SearchMethodCount(search_type, keyword, prin.getUser_group());
		
		int offset = (page_num - 1) * limit;
		int end_page = (count + limit - 1) / limit;
		
		List<Method> method = service.SearchMethod(search_type, keyword, offset, limit, prin.getUser_group());
		
		result.put("method", method);
		result.put("page_num", page_num);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
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
	public List<Research> SelectResearch(Authentication auth, @RequestParam("division_id") int division_id)
	{
		
		User prin = (User)auth.getPrincipal();
		
		List<Research> result = service.SelectResearch(division_id, prin.getUser_group());
		
		return result;
	}
	
	// 조사방법 등록
	@ResponseBody
	@RequestMapping("insertResearch")
	public int InsertResearch(Authentication auth, @RequestParam("division_id") int division_id, @RequestParam("research_contents") String research_contents)
	{
		User prin = (User)auth.getPrincipal();
		
		Research research = new Research();
		Research last_research = researchService.SelectLastResearch(prin.getUser_group());
		
		Calendar cal = Calendar.getInstance();
		
		
		String code1 = "ac-";
		String code2 = String.valueOf(cal.get(Calendar.YEAR))+"-";
		
		if(last_research == null) {
			research.setResearch_code(code1+code2+"00001");
		}else {
			String last_research_code = last_research.getResearch_code();
			String[] strArr = last_research_code.split("-");
			int code3 = Integer.parseInt(strArr[2]) + 1;
			research.setResearch_code(code1+code2+String.format("%05d", code3));
		}
		
		research.setUser_group(prin.getUser_group());
		research.setDivision_id(division_id);
		research.setResearch_contents(research_contents);
		
		int result = researchService.InsertResearch(research);
		
		return result;
	}
	
	// 재배 프로토콜 등록
	@ResponseBody
	@RequestMapping("insertMethod")
	public int InsertMethod(Authentication auth, Method method)
	{
		
		User prin = (User)auth.getPrincipal();
		
		Calendar cal = Calendar.getInstance();
		
		String last_reagent_code = service.selectLastMethodCode(prin.getUser_group());
		String code1 = "mv-";
		String code2 = String.valueOf(cal.get(Calendar.YEAR))+"-";
		String resultCode = "";
		
		if(last_reagent_code == null || last_reagent_code.equals("")) {
			resultCode = code1+code2+"00001";
			method.setMethod_code(resultCode);
		}else {
			String[] strArr = last_reagent_code.split("-");
			int code3 = Integer.parseInt(strArr[2]) + 1;
			resultCode = code1+code2+String.format("%05d", code3);
			method.setMethod_code(resultCode);
		}
		
		method.setUser_group(prin.getUser_group());
		int result = service.InsertMethod(method);
		
		if(result != 0) {
			result = method.getLast_method_id();
			//등록 성공 후 record 등록
			Record record = new Record();
			record.setRecord_status(0);
			record.setRecord_type(method.getLast_method_id());
			record.setRecord_type_code(resultCode);
			service.insertRecord(record);
		}
		
		return result;
	}
	
	// 실험, 재배 단계 등록
	@ResponseBody
	@RequestMapping("/insertStep")
	public int InsertStep(@RequestBody List<Step> steps)
	{
		System.out.println("등록된 step 개수 : " + steps.size());
		int insertResult[] = new int[steps.size()];
		int result = 0;
		
		for(int i = 0; i < steps.size(); i++) {
			insertResult[i] = service.InsertStep(steps.get(i));
		}
		
		Boolean isInsert = IntStream.of(insertResult).noneMatch(x -> x == 0);
		
		if(isInsert) {
			result = 1;
		}else {
			result = 0;
		}
		
		return result;
	}
	
	// 재배 프로토콜 수정 화면
	@RequestMapping("modify")
	public ModelAndView MethodModify(ModelAndView mv, @RequestParam(name = "method_id", required = true) int method_id)
	{
		
		Method method = service.selectMethodDetail(method_id);
		List<Division> d1 = researchService.SelectDivision(0, 0);
		List<Division> d2 = researchService.SelectDivision(method.getD1_id(), 1);
		List<Division> d3 = researchService.SelectDivision(method.getD2_id(), 2);
		
		List<Step> step = service.selectStepDetailList(method_id);
		
		List<Record> record = service.selectRecordList(method_id);
		
		mv.addObject("method", method);
		mv.addObject("d1", d1);
		mv.addObject("d2", d2);
		mv.addObject("d3", d3);
		mv.addObject("step", step);
		mv.addObject("record", record);
		
		mv.setViewName("method/method_modify");
		
		return mv;
	}
	
	// 재배 프로토콜 수정
	@ResponseBody
	@RequestMapping("updateMethod")
	public int UpdateMethod(Method method)
	{
		System.out.println("업데이트");
		System.out.println(method);
		int result = service.updateMethod(method);
		
		if(result != 0) {
			//수정 성공 후 변경 이력 등록
			Record record = new Record();
			record.setRecord_status(1);
			record.setRecord_type(method.getMethod_id());
			record.setRecord_type_code(method.getMethod_code());
			service.insertRecord(record);
		}
		
		return result;
	}
	
	// step 삭제
	@ResponseBody
	@RequestMapping("deleteStep")
	public int DeleteStep(@RequestParam(name = "cancelArr") List<Integer> cancel)
	{
		
		int deleteResult[] = new int[cancel.size()];
		int result = 0;
		
		for(int i = 0; i < cancel.size(); i++) {
			deleteResult[i] = service.deleteStep(cancel.get(i));
			System.out.println("step 삭제 id : " + cancel.get(i));
		}
		
		Boolean isDelete = IntStream.of(deleteResult).noneMatch(x -> x == 0);
		
		if(isDelete) {
			result = 1;
		}else {
			result = 0;
		}
		
		return result;
	}
	
	// 재배 프로토콜 삭제
	@ResponseBody
	@RequestMapping("deleteMethod")
	public int DeleteMethod(@RequestParam(name = "method_id") int method_id)
	{
		
		int result = service.deleteMethod(method_id);
		
		return result;
	}
	
	// 프로토콜 승인
	@ResponseBody
	@RequestMapping("updateStatus")
	public int UpdateStatus(@RequestParam(name = "method_id") int method_id, @RequestParam(name = "method_code") String method_code)
	{
		
		int result = service.updateMethodStatus(method_id);
		
		if(result != 0) {
			Record record = new Record();
			record.setRecord_status(2);
			record.setRecord_type(method_id);
			record.setRecord_type_code(method_code);
			service.insertRecord(record);
		}
		
		return result;
	}
	
	
}