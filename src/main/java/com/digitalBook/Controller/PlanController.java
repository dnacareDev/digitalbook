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

import com.digitalBook.Entity.Department;
import com.digitalBook.Entity.Etc;
import com.digitalBook.Entity.Factor;
import com.digitalBook.Entity.Fertilizer;
import com.digitalBook.Entity.Manure;
import com.digitalBook.Entity.Method;
import com.digitalBook.Entity.Plan;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Entity.Storage;
import com.digitalBook.Entity.User;
import com.digitalBook.Service.PlanService;
import com.digitalBook.Service.StorageService;

@Controller
@RequestMapping("plan")
public class PlanController
{
	
	@Autowired
	private PlanService service;
	
	@Autowired StorageService storageService;
	
	//재배계획 목록 페이지
	@RequestMapping("/list")
	public ModelAndView PlanList(ModelAndView mv)
	{
		
		List<Department> department = storageService.SelectDepartment();
		
		mv.addObject("department", department);
		
		mv.setViewName("plan/plan_list");
		
		return mv;
	}
	
	//재배계획 등록 페이지
	@RequestMapping("/insert")
	public ModelAndView PlanInsert(Authentication auth, ModelAndView mv)
	{
		
		User prin = (User)auth.getPrincipal();
		
		List<Report> report = service.selectReportList();
		List<Fertilizer> fert = service.selectFertilizerList(0, 0);
		List<Method> method = service.selectMethodList(prin.getUser_group());
		
		mv.addObject("report", report);
		mv.addObject("fert", fert);
		mv.addObject("method", method);
		
		mv.setViewName("plan/plan_insert");
		
		return mv;
	}
	
	//seed list 조회
	@ResponseBody
	@RequestMapping("/selectSeed")
	public List<Seed> SelectSeed(Authentication auth,
								@RequestParam("report_code") String report_code)
	{
		
		User prin = (User)auth.getPrincipal();
		
		List<Seed> seed = service.selectSeedList(prin.getUser_group(), report_code);
		
		return seed;
	}
	
	//fertilizer list 조회
	@ResponseBody
	@RequestMapping("/selectFertilizer")
	public List<Fertilizer> SelectFertilizer(@RequestParam("fert_id") int fert_id)
	{
		List<Fertilizer> fert = service.selectFertilizerList(1, fert_id);
		
		return fert;
	}
	
	//재배계획 등록
	@ResponseBody
	@RequestMapping("/insertPlan")
	public int InsertPlan(Authentication auth, Plan plan)
	{
		
		User prin = (User)auth.getPrincipal();
		
		Calendar cal = Calendar.getInstance();
		
		String last_plan_code = service.selectLastPlanCode(prin.getUser_group());
		String code1 = "vp-";
		String code2 = String.valueOf(cal.get(Calendar.YEAR))+"-";
		String resultCode = "";
		
		if(last_plan_code == null || last_plan_code.equals("")) {
			resultCode = code1+code2+"00001";
			plan.setPlan_code(resultCode);
		}else {
			String[] strArr = last_plan_code.split("-");
			int code3 = Integer.parseInt(strArr[2]) + 1;
			resultCode = code1+code2+String.format("%05d", code3);
			plan.setPlan_code(resultCode);
		}
		
		plan.setUser_group(prin.getUser_group());
		int result = service.insertPlan(plan);
		
		if(result != 0) {
			result = plan.getLast_plan_id();
		}
		
		return result;
	}
	
	//시험구배치 요인 등록
	@ResponseBody
	@RequestMapping("/insertFactor")
	public int InsertFactor(@RequestBody List<Factor> factors)
	{
		
		int insertResult[] = new int[factors.size()];
		int result = 0;
		
		for(int i = 0; i < factors.size(); i++) {
			insertResult[i] = service.insertFactor(factors.get(i));
		}
		
		Boolean isInsert = IntStream.of(insertResult).noneMatch(x -> x == 0);
		
		if(isInsert) {
			result = 1;
		}else {
			result = 0;
		}
		
		return result;
	}
	
	//기타 요인 등록
	@ResponseBody
	@RequestMapping("/insertEtc")
	public int[] InsertEtc(@RequestBody List<Etc> etcs)
	{
		
		int insertResult[] = new int[etcs.size()];
		int returnResult[] = new int[etcs.size()];
		
		for(int i = 0; i < etcs.size(); i++) {
			insertResult[i] = service.insertEtc(etcs.get(i));
		}
		
		Boolean isInsert = IntStream.of(insertResult).noneMatch(x -> x == 0);
		
		if(isInsert) {
			for(int i = 0; i < etcs.size(); i++) {
				returnResult[i] = etcs.get(i).getLast_etc_id();
			}
		}else {
			returnResult[0] = 0;
		}
		
		return returnResult;
	}
	
	//시비량 등록
	@ResponseBody
	@RequestMapping("/insertManure")
	public int[] InsertManure(@RequestBody List<Manure> manures)
	{
		
		int insertResult[] = new int[manures.size()];
		int returnResult[] = new int[manures.size()];
		
		for(int i = 0; i < manures.size(); i++) {
			insertResult[i] = service.insertManure(manures.get(i));
		}
		
		Boolean isInsert = IntStream.of(insertResult).noneMatch(x -> x == 0);
		
		if(isInsert) {
			for(int i = 0; i < manures.size(); i++) {
				returnResult[i] = manures.get(i).getLast_manure_id();
			}
		}else {
			returnResult[0] = 0;
		}
		
		return returnResult;
		
	}
	
	// 재배 프로토콜 검색
	@ResponseBody
	@RequestMapping("/searchPlan")
	public Map<String, Object> SearchPlan(Authentication auth,
											@RequestParam(name = "search_type", required = false) String search_type,
											@RequestParam(name = "keyword", required = false) String keyword,
											@RequestParam("page_num") int page_num,
											@RequestParam("limit") int limit)
	{
		
		User prin = (User)auth.getPrincipal();
		
		Map<String, Object> result = new LinkedHashMap<>();
		
		int count = service.SearchPlanCount(search_type, keyword, prin.getUser_group());
		
		int offset = (page_num - 1) * limit;
		int end_page = (count + limit - 1) / limit;
		
		List<Plan> plan = service.SearchPlan(search_type, keyword, offset, limit, prin.getUser_group());
		
		result.put("plan", plan);
		result.put("page_num", page_num);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
	
	//실험장소 검색
	@ResponseBody
	@RequestMapping("/searchStorage")
	public Map<String, Object> SearchStorage(@RequestParam("page_num") int page_num)
	{
		Map<String, Object> result = new LinkedHashMap<String, Object>();;
		
		int count = storageService.SelectStorageCount();
		
		int limit = 10;
		int offset = (page_num - 1) * limit;
		int end_page = (count + limit - 1) / limit;
		
		List<Storage> storage = storageService.SearchStorage(offset, limit);
		
		result.put("storage", storage);
		result.put("page_num", page_num);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
	
	//재배계획 수정 화면
	@RequestMapping("/modify")
	public ModelAndView PlanModify(Authentication auth, ModelAndView mv, @RequestParam(name = "plan_id", required = true) int plan_id)
	{
		
		User prin = (User)auth.getPrincipal();
		
		Plan plan = service.selectPlanDetail(plan_id);
		List<Report> report = service.selectReportList();
		List<Fertilizer> fert = service.selectFertilizerList(0, 0);
		List<Method> method = service.selectMethodList(prin.getUser_group());
		List<Factor> factor = service.selectFactorList(plan_id);
		
		mv.addObject("plan", plan);
		mv.addObject("report", report);
		mv.addObject("fert", fert);
		mv.addObject("method", method);
		mv.addObject("factor", factor);
		
		mv.setViewName("plan/plan_modify");
		
		return mv;
	}
	
	//재배계획 수정
	@ResponseBody
	@RequestMapping("/updatePlan")
	public int UpdatePlan(Plan plan)
	{
		
		int result = service.updatePlan(plan);
		System.out.println(plan);
		
		return result;
	}
	
	//시비량 list
	@ResponseBody
	@RequestMapping("/selectManure")
	public List<Manure> SelectManure(@RequestParam("plan_id") int plan_id)
	{
		
		List<Manure> manure = service.selectManureList(plan_id);
		
		return manure;
	}
	
	//기타 list
	@ResponseBody
	@RequestMapping("/selectEtc")
	public List<Etc> SelectEtc(@RequestParam("plan_id") int plan_id)
	{
		List<Etc> etc = service.selectEtcList(plan_id);
		
		return etc;
	}
	
	//시비량 삭제
	@ResponseBody
	@RequestMapping("/deleteManure")
	public int DeleteManure(@RequestParam(name = "cancelArr") List<Integer> cancel)
	{
		int deleteResult[] = new int[cancel.size()];
		int result = 0;
		
		for(int i = 0; i < cancel.size(); i++) {
			deleteResult[i] = service.deleteManure(cancel.get(i));
		}
		
		Boolean isDelete = IntStream.of(deleteResult).noneMatch(x -> x == 0);
		
		if(isDelete) {
			result = 1;
		}else {
			result = 0;
		}
		
		return result;
	}
	
	//기타 요인 삭제
	@ResponseBody
	@RequestMapping("/deleteEtc")
	public int DeleteEtc(@RequestParam(name = "cancelArr") List<Integer> cancel)
	{
		int deleteResult[] = new int[cancel.size()];
		int result = 0;
		
		for(int i = 0; i < cancel.size(); i++) {
			deleteResult[i] = service.deleteEtc(cancel.get(i));
		}
		
		Boolean isDelete = IntStream.of(deleteResult).noneMatch(x -> x == 0);
		
		if(isDelete) {
			result = 1;
		}else {
			result = 0;
		}
		
		return result;
	}
	
	//시험구 배치 요인 삭제
	@ResponseBody
	@RequestMapping("/deleteFactor")
	public int DeleteFactor(@RequestParam(name = "cancelArr") List<Integer> cancel)
	{
		int deleteResult[] = new int[cancel.size()];
		int result = 0;
		
		for(int i = 0; i < cancel.size(); i++) {
			deleteResult[i] = service.deleteFactor(cancel.get(i));
		}
		
		Boolean isDelete = IntStream.of(deleteResult).noneMatch(x -> x == 0);
		
		if(isDelete) {
			result = 1;
		}else {
			result = 0;
		}
		
		return result;
	}
	
}