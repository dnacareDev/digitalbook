package com.digitalBook.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Department;
import com.digitalBook.Entity.Etc;
import com.digitalBook.Entity.Factor;
import com.digitalBook.Entity.Fertilizer;
import com.digitalBook.Entity.Manure;
import com.digitalBook.Entity.Method;
import com.digitalBook.Entity.Plan;
import com.digitalBook.Entity.Record;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Results;
import com.digitalBook.Entity.Schedule;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Entity.Segment;
import com.digitalBook.Entity.SegmentInfo;
import com.digitalBook.Entity.Storage;
import com.digitalBook.Entity.User;
import com.digitalBook.Service.PlanService;
import com.digitalBook.Service.StorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("plan")
public class PlanController
{
	
	@Autowired
	private PlanService service;
	
	@Autowired StorageService storageService;
	
	@Autowired
	private FileController fileController;
	
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
		List<User> user = service.selectUserList(prin.getUser_group());
		
		mv.addObject("report", report);
		mv.addObject("fert", fert);
		mv.addObject("method", method);
		mv.addObject("user", user);
		
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
		
		service.updateStorageStatus(plan.getStorage_id());
		plan.setUser_group(prin.getUser_group());
		int result = service.insertPlan(plan);
		
		if(result != 0) {
			result = plan.getLast_plan_id();
			Record record = new Record();
			record.setRecord_status(0);
			record.setRecord_type(plan.getLast_plan_id());
			record.setRecord_type_code(resultCode);
			service.insertRecord(record);
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
	
	//재배계획 검색
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
		List<User> user = service.selectUserList(prin.getUser_group());
		List<Schedule> sch = service.selectScheduleList(plan_id);
		
		List<Record> record = service.selectRecordList(plan_id);
		
		mv.addObject("plan", plan);
		mv.addObject("report", report);
		mv.addObject("fert", fert);
		mv.addObject("method", method);
		mv.addObject("factor", factor);
		mv.addObject("user", user);
		mv.addObject("record", record);
		mv.addObject("sch", sch);
		
		mv.setViewName("plan/plan_modify");
		
		return mv;
	}
	
	//재배계획 수정
	@ResponseBody
	@RequestMapping("/updatePlan")
	public int UpdatePlan(Plan plan)
	{
		
		int result = service.updatePlan(plan);
		
		if(result != 0) {
			//수정 성공 후 변경 이력 등록
			Record record = new Record();
			record.setRecord_status(1);
			record.setRecord_type(plan.getPlan_id());
			record.setRecord_type_code(plan.getPlan_code());
			service.insertRecord(record);
		}
		
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
	
	//재배계획 승인
	@ResponseBody
	@RequestMapping("/updateStatus")
	public int UpdateStatus(@RequestParam(name = "plan_id") int plan_id, @RequestParam(name = "plan_code") String plan_code)
	{
		
		int result = service.updatePlanStatus(plan_id);
		
		if(result != 0) {
			Record record = new Record();
			record.setRecord_status(2);
			record.setRecord_type(plan_id);
			record.setRecord_type_code(plan_code);
			service.insertRecord(record);
		}
		
		return result;
	}
	
	//담당자 등록
	@ResponseBody
	@RequestMapping("insertSchedule")
	public int insertSchedule(@RequestBody List<Schedule> schedule)
	{
		int insertResult[] = new int[schedule.size()];
		int result = 0;
		
		for(int i = 0; i < schedule.size(); i++) {
			insertResult[i] = service.insertSchedule(schedule.get(i));
		}
		
		Boolean isInsert = IntStream.of(insertResult).noneMatch(x -> x == 0);
		
		if(isInsert) {
			result = 1;
		}else {
			result = 0;
		}
		
		return result;
	}
	
	//담당자 수정
	@ResponseBody
	@RequestMapping("updateSchedule")
	public int updateSchedule(@RequestBody List<Schedule> schedule, @RequestParam("plan_code") String plan_code)
	{
		int insertResult[] = new int[schedule.size()];
		int result = 0;
		
		for(int i = 0; i < schedule.size(); i++) {
			insertResult[i] = service.insertSchedule(schedule.get(i));
		}
		
		Boolean isInsert = IntStream.of(insertResult).noneMatch(x -> x == 0);
		
		if(isInsert) {
			result = 1;
			service.updatePlanStatus1(schedule.get(0).getPlan_id());
			//수정 성공 후 변경 이력 등록
			Record record = new Record();
			record.setRecord_status(1);
			record.setRecord_type(schedule.get(0).getPlan_id());
			record.setRecord_type_code(plan_code);
			service.insertRecord(record);
		}else {
			result = 0;
		}
		
		return result;
	}
	
	//결과입력 목록 페이지
	@RequestMapping("/result/list")
	public ModelAndView ResultList(ModelAndView mv)
	{
		
		List<Department> department = storageService.SelectDepartment();
		
		mv.addObject("department", department);
		
		mv.setViewName("plan/result_list");
		
		return mv;
	}
	
	//결과입력 등록 화면
	@RequestMapping("/insertResult")
	public ModelAndView insertResult(Authentication auth, ModelAndView mv, @RequestParam(name = "plan_id", required = true) int plan_id)
	{
		
		User prin = (User)auth.getPrincipal();
		
		Plan plan = service.selectPlanDetail(plan_id);
		List<User> user = service.selectUserList(prin.getUser_group());
		List<Schedule> sch = service.selectScheduleList(plan_id);
		
		List<Record> record = service.selectRecordList(plan_id);
		
		List<Results> results = service.selectResultsList(plan_id);
		
		List<Segment> segment = service.selectSegmentList(plan_id);
		List<SegmentInfo> segmentInfo = service.selectSegmentInfoList(plan_id);
		
		int arr[] = Arrays.stream(plan.getPlan_method().split(",")).mapToInt(Integer::parseInt).toArray();
		List<Method> method = service.selectPlanMethodList(arr);
		
		mv.addObject("plan", plan);
		mv.addObject("user", user);
		mv.addObject("record", record);
		mv.addObject("sch", sch);
		mv.addObject("method", method);
		mv.addObject("results", results);
		mv.addObject("segment", segment);
		mv.addObject("segmentInfo", segmentInfo);
		
		mv.setViewName("plan/result_insert");
		
		return mv;
	}
	
	//시비량 삭제
	@ResponseBody
	@RequestMapping("/deleteSchedule")
	public int DeleteSchedule(@RequestParam(name = "cancelSch") List<Integer> cancel)
	{
		int deleteResult[] = new int[cancel.size()];
		int result = 0;
		
		for(int i = 0; i < cancel.size(); i++) {
			deleteResult[i] = service.deleteSchedule(cancel.get(i));
		}
		
		Boolean isDelete = IntStream.of(deleteResult).noneMatch(x -> x == 0);
		
		if(isDelete) {
			result = 1;
		}else {
			result = 0;
		}
		
		return result;
	}
	
	//포장 등록
	@ResponseBody
	@RequestMapping("insertStorage")
	public int InsertStorage(Authentication auth, Storage storage)
	{
		
		User prin = (User)auth.getPrincipal();
		Calendar cal = Calendar.getInstance();
		
		int now = cal.get(Calendar.YEAR);
		
		Storage last_storage = storageService.SelectLastStorage();
		
		if(last_storage == null)
		{
			storage.setStorage_code("fi-" + now + "-00001");
		}
		else
		{
			String[] strArr = last_storage.getStorage_code().split("-");
			
			int year = Integer.parseInt(strArr[1]);
			
			if(year == now)
			{
				int code = Integer.parseInt(strArr[2]) + 1;
				
				storage.setStorage_code("fi-" + now + "-" + String.format("%05d", code));
			}
			else
			{
				storage.setStorage_code("fi-" + now + "-00001");
			}
		}
		
		storage.setDepart_id(prin.getUser_group());
		int result = service.InsertStorage(storage);
		
		return result;
	}
	
	//결과입력 등록
	@ResponseBody
	@RequestMapping(value = "/insertResults")
	public int InsertResult(MultipartHttpServletRequest req, HttpServletRequest requst, @RequestParam(name = "cancelArr") int cancel[]) throws IOException
	{
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<MultipartFile> files = req.getFiles("file");
		
//		System.out.println(requst.getParameterValues("index")[0]);
//		System.out.println(files.size());
//		System.out.println(requst.getParameterValues("result")[0]);
		
		
		//result list 만들기
		JsonNode node = mapper.readTree(requst.getParameterValues("result")[0]);
		
		List<Results> results = new ArrayList<>();
		
		for(int i = 0; i < node.size(); i++) {
			String input = mapper.writeValueAsString(node.get(i));
			Results r = mapper.readValue(input, Results.class);
			results.add(r);
		}
		
		//index list 만들기
		String indexString = (String) requst.getParameterValues("index")[0];
		
		JsonNode indexNode = mapper.readTree(indexString);
		List<String> indexList = new ArrayList<>();
		for(int i = 0; i < indexNode.size(); i++) {
			indexList.add(indexNode.get(i).get("index").asText());
		}
		
		int result = 0;
		
		if(files.size() != 0) {
			result = SaveResultImg(files, indexList, results, cancel);
			System.out.println(results.size()+"개 등록 완료");
		}else {
			
			result = service.insertResults(results);
			
			if(result != 0) {
				if(cancel.length != 0) {
					service.deleteResults(cancel);
				}
			}
			System.out.println(results.size()+"개 등록 완료");
		}
		
		return result;
	}
	
	//결과입력 사진 저장
	public int SaveResultImg(List<MultipartFile> files, List<String> indexList, List<Results> results, int cancel[]) throws IOException
	{
		
		for(int i = 0; i < indexList.size(); i++) {
			
			for(int j = 0; j < results.size(); j++) {
				
				String check = results.get(j).getSegment_id()+"-"+results.get(j).getIndividual_id()+"-"+results.get(j).getIndividual_index();
				
				if(check.equals(indexList.get(i))) {
					
					String[] extension = files.get(i).getOriginalFilename().split("\\.");
					
					String results_file = fileController.ChangeFileName(extension[1]);
					String results_origin_file = files.get(i).getOriginalFilename();
					
					String path = "upload";
					
					File filePath = new File(path);
					
					if (!filePath.exists())
			            filePath.mkdirs();
					
					Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
			       	Path targetLocation = fileLocation.resolve(results_file);
					
			       	Files.copy(files.get(i).getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
					
					results.get(j).setResults_file(results_file);
					results.get(j).setResults_origin_file(results_origin_file);
				}
				
			}//end for2
			
		}//end for
		
		int result = service.insertResults(results);
		
		if(result != 0) {
			if(cancel.length != 0) {
				service.deleteResults(cancel);
			}
		}
		
		return result;
	}
	
	//구획 등록
	@ResponseBody
	@RequestMapping("/insertSegment")
	public int InsertSegment(@RequestBody List<Segment> segment)
	{
		
		int result = service.insertSegment(segment);
		
		return result;
	}
	
	//구획정보 등록
	@ResponseBody
	@RequestMapping("/insertSegmentInfo")
	public int InsertSegmentInfo(@RequestBody List<SegmentInfo> segmentInfo, @RequestParam(name = "cancelInfo") int cancel[])
	{
		
		int result =service.insertSegmentInfo(segmentInfo);
		
		if(result != 0) {
			if(cancel.length != 0) {
				service.deleteSegmentInfo(cancel);
			}
		}
		
		return result;
	}
	
}