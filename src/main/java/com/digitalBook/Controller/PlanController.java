package com.digitalBook.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.digitalBook.Entity.ResultPlan;
import com.digitalBook.Entity.Results;
import com.digitalBook.Entity.Schedule;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Entity.Segment;
import com.digitalBook.Entity.SegmentInfo;
import com.digitalBook.Entity.Storage;
import com.digitalBook.Entity.User;
import com.digitalBook.Entity.WeatherSoilInfo;
import com.digitalBook.Service.PlanService;
import com.digitalBook.Service.StorageService;
import com.fasterxml.jackson.core.type.TypeReference;
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
		
		List<Report> report = service.selectReportList(prin.getUser_name_k());
		List<Fertilizer> fert = service.selectFertilizerList(0, 0);
		List<Method> method = service.selectMethodList(prin.getUser_group(), prin.getUser_id());
		List<User> user = service.selectUserList(prin.getUser_group());
		
		List<Department> department = storageService.SelectDepartment();
		
		mv.addObject("department", department);
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
		
		
		plan.setUser_id(prin.getUser_id());
		
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
		
		service.updateStorageStatus(plan.getStorage_id(), 1);
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
											@RequestParam(name = "isMineOnly", required = false) int isMineOnly,
											@RequestParam("page_num") int page_num,
											@RequestParam("limit") int limit)
	{
		
		User prin = (User)auth.getPrincipal();
		
		Map<String, Object> result = new LinkedHashMap<>();
		
		Plan planParam = new Plan();
		planParam.setSearch_type(search_type);
		planParam.setUser_group(prin.getUser_group());
		planParam.setIsMineOnly(isMineOnly);
		planParam.setUser_id(isMineOnly == 1 ? prin.getUser_id() : 0);
		planParam.setPage_num(page_num);
		planParam.setKeyword(keyword);
		planParam.setLimit(limit);
		
		int count = service.SearchPlanCount(planParam);
		
		int offset = (page_num - 1) * limit;
		int start_page = ((page_num - 1) / 10) * 10 + 1;
		int end_page = (count + limit - 1) / limit;
		planParam.setOffset(offset);
		
		List<Plan> plan = service.SearchPlan(planParam);
		
		result.put("plan", plan);
		result.put("page_num", page_num);
		result.put("start_page", start_page);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
	
	//결과입력 검색
	@ResponseBody
	@RequestMapping("/searchResultPlan")
	public Map<String, Object> SearchResultPlan(Authentication auth,
											@RequestParam(name = "search_type", required = false) String search_type,
											@RequestParam(name = "keyword", required = false) String keyword,
											@RequestParam(name = "isMineOnly", required = false) int isMineOnly,
											@RequestParam("page_num") int page_num,
											@RequestParam("limit") int limit,
											@RequestParam("plan_step") int plan_step)
	{
		
		User prin = (User)auth.getPrincipal();
		
		Map<String, Object> result = new LinkedHashMap<>();
		Plan planParam = new Plan();
		planParam.setSearch_type(search_type);
		planParam.setUser_group(prin.getUser_group());
		planParam.setIsMineOnly(isMineOnly);
		planParam.setUser_id(isMineOnly == 1 ? prin.getUser_id() : 0);
		planParam.setPage_num(page_num);
		planParam.setKeyword(keyword);
		planParam.setLimit(limit);
		
		int count = service.SearchResultPlanCount(planParam);
		
		int offset = (page_num - 1) * limit;
		int start_page = ((page_num - 1) / 10) * 10 + 1;
		int end_page = (count + limit - 1) / limit;
		planParam.setOffset(offset);
		
		List<Plan> plan = service.SearchResultPlan(planParam);
		
		result.put("plan", plan);
		result.put("page_num", page_num);
		result.put("start_page", start_page);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
	
	//실험장소 검색
	@ResponseBody
	@RequestMapping("/searchStorage")
	public Map<String, Object> SearchStorage(Authentication auth, @RequestParam("page_num") int page_num)
	{
		
		User prin = (User)auth.getPrincipal();
		
		Map<String, Object> result = new LinkedHashMap<String, Object>();;
		
		int count = service.SelectStorageCount(prin.getUser_id());
		
		int limit = 10;
		int offset = (page_num - 1) * limit;
		int start_page = ((page_num - 1) / 10) * 10 + 1;
		int end_page = (count + limit - 1) / limit;
		
		List<Storage> storage = service.SearchStorage(offset, limit, prin.getUser_id());
		
		result.put("storage", storage);
		result.put("page_num", page_num);
		result.put("start_page", start_page);
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
		List<Report> report = service.selectReportList(prin.getUser_name_k());
		List<Fertilizer> fert = service.selectFertilizerList(0, 0);
		List<Method> method = service.selectMethodList(prin.getUser_group(), prin.getUser_id());
		List<Factor> factor = service.selectFactorList(plan_id);
		List<User> user = service.selectUserList(prin.getUser_group());
		List<Schedule> sch = service.selectScheduleList(plan_id);
		
		List<Record> record = service.selectRecordList(plan_id);
		
		List<Segment> segment = service.selectSegmentList(plan_id);
		
		mv.addObject("plan", plan);
		mv.addObject("report", report);
		mv.addObject("fert", fert);
		mv.addObject("method", method);
		mv.addObject("factor", factor);
		mv.addObject("user", user);
		mv.addObject("record", record);
		mv.addObject("sch", sch);
		mv.addObject("segment", segment);
		
		mv.setViewName("plan/plan_modify");
		
		return mv;
	}
	
	//재배계획 수정
	@ResponseBody
	@RequestMapping("/updatePlan")
	public int UpdatePlan(Plan plan, @RequestParam("exist_storage") int exist_storage)
	{
		
		if(exist_storage != 0) {
			service.updateStorageStatus(plan.getStorage_id(), 1);
			service.updateStorageStatus(exist_storage, 0);
		}
		
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
		List<Schedule> sch = service.selectScheduleList(plan_id);
		
		List<Record> record = service.selectRecordList(plan_id);
		
		List<Results> results = new ArrayList<Results>();//service.selectResultsList(plan_id);
		ResultPlan resultPlan = service.selectResultPlanOne(plan_id);
		List<Segment> segment = service.selectSegmentList(plan_id);
		List<SegmentInfo> segmentInfo = service.selectSegmentInfoList(plan_id);

//		List<HashMap<String,Object>> addressForSoil = service.selectAreaCode();

		List<HashMap<String,Object>> addressForWeather = service.addressForWeather();
		
		if(!plan.getPlan_method().isEmpty()) {			
			int arr[] = Arrays.stream(plan.getPlan_method().split(",")).mapToInt(Integer::parseInt).toArray();
			List<Method> method = service.selectPlanMethodList(arr);
			mv.addObject("method", method);
		}
		
		Map<String,Object> map = service.selectWeatherSoilInfo(plan_id);
	
		if(map != null) {
			// 토양및 기상정보
			mv.addObject("map",map);
		}else {			
			mv.addObject("map","noData");
		}
		
		mv.addObject("plan_id", plan_id);
		mv.addObject("plan", plan);
		mv.addObject("record", record);
		mv.addObject("sch", sch);
		mv.addObject("results", results);
		mv.addObject("resultPlan",resultPlan);
		mv.addObject("segment", segment);
		mv.addObject("segmentInfo", segmentInfo);
		
		// 토양 정보 address code
//		mv.addObject("addressForSoil",addressForSoil);
		mv.addObject("addressForWeather",addressForWeather);		
		
		
		mv.setViewName("plan/result_insert");
		
		return mv;
	}
	
	@RequestMapping("/addressForSoil")
	@ResponseBody
	public List<HashMap<String,Object>> addressForSoil(){
		List<HashMap<String,Object>> addressForSoil = service.selectAreaCode();
		
		return addressForSoil;
	}
	
	@RequestMapping("/findWeather")
	@ResponseBody
	public Map<String,Object> findWeather(@RequestParam(name = "area_name", required = true) String area_name)
	{
		Map<String,Object> result = new HashMap<String,Object>();
		System.out.println(area_name);
		List<Map<String,Object>> map = service.findWeather(area_name); 
		
		result.put("data", map);
		return result;
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
		
		// 좌표 설정시 주소에서 m2 제거하기
		if(storage.getStorage_size() != null) {			
			String[] storage_size = storage.getStorage_size().split(" ");
			storage.setStorage_size(storage_size[0]);
			storage.setStorage_unit(storage_size[1]);
		}
		
		storage.setUser_id(prin.getUser_id());
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
	
	@ResponseBody
	@RequestMapping(value = "/insertImage")
	public Map<String, Object> InsertImageResult(MultipartFile file, HttpServletRequest requst) 
	{

		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		String[] extension = file.getOriginalFilename().split("\\.");
		
		String results_file = fileController.ChangeFileName(extension[1]);
		String results_origin_file = file.getOriginalFilename();
		
		String path = "/var/www/html/upload";
		
		File filePath = new File(path);
		
		if (!filePath.exists())
            filePath.mkdirs();
		
		Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
       	Path targetLocation = fileLocation.resolve(results_file);
		
       	try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

       	result.put("result","1");
       	result.put("file_name",results_file);
       	
		return result;
	}
	

	//결과입력 등록
	@ResponseBody
	@RequestMapping(value = "/insertResultPlan")
	public int InsertResult(ResultPlan results) throws IOException
	{
	
		// 중복 확인 
		int count = service.checkResultsPlan(results.getPlan_id());
		
		int result = 0;
		if(1 > count) {			
			result = service.insertResultsPlan(results);
		}else {
			result = service.updateResultsPlan(results);
		}
		
	
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateResultPlan")
	public int updateResultPlan(ResultPlan results) throws IOException
	{
		int result = service.updateResultPlan(results);
		return result > 0 ? 1 : 0;
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
	public int InsertSegment(@RequestBody List<Segment> segment, @RequestParam(name = "cancel") int cancel[])
	{
		
		int result = service.insertSegment(segment);
		
		if(result != 0) {
			if(cancel.length != 0) {
				service.deleteSegment(cancel);
			}
		}
		
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
	
	//사진 다운로드
	@ResponseBody
	@RequestMapping("/downloadImg")
	public ResponseEntity<Object> DownloadImg(@RequestParam("file_name") String file_name)
	{
		String path = "upload/" + file_name;
		
		try
		{
			Path filePath = Paths.get(path);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
			
			File file = new File(path);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());  // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
			
			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		
	}
	
	//재배계획 엑셀 다운로드
	@RequestMapping(value = "/exceldownload", produces = "application/vnd.ms-excel")
	public String Exceldownload(Authentication auth, Model model) 
	{
		
		User prin = (User)auth.getPrincipal();
		model.addAttribute("user_group", prin.getUser_group());
		
		return "planExcelView";
	}
	
	//결과입력 엑셀 다운로드
	@RequestMapping(value = "/result/exceldownload", produces = "application/vnd.ms-excel")
	public String ResultExceldownload(Authentication auth, Model model) 
	{
		
		User prin = (User)auth.getPrincipal();
		model.addAttribute("user_group", prin.getUser_group());
		model.addAttribute("plan_step", 1);
		
		return "resultExcelView";
	}
	
	//sample_report 화면
	@RequestMapping("/sampleReport")
	public String SampleReport(Model model, @RequestParam("report_code") String report_code)
	{
		
		Report report = service.selectSampleReportDetail(report_code);
		
		model.addAttribute("report", report);
		
		return "plan/sample_report";
	}
	
	//segment 가져오기
	@RequestMapping("/getSegment")
	@ResponseBody
	public List<Segment> getSegment(@RequestParam("plan_id") int plan_id)
	{
		List<Segment> segment = service.selectSegmentList(plan_id);
		
		return segment;
	}
	
	//sample_segment 화면
	@RequestMapping("sampleSegment")
	public String SampleSegment(Model model, @RequestParam("report_code") String report_code, @RequestParam("segment_id") int segment_id)
	{
		
		Report report = service.selectSampleReportDetail(report_code);
		Segment segment = service.selectSampleSegmentDetail(segment_id);
		
		model.addAttribute("report", report);
		model.addAttribute("segment", segment);
		
		return "plan/sample_segment";
	}
	
	// 토양 정보 불러오는 API
	@RequestMapping("getSoilInfo")
	@ResponseBody
	public Map<String,Object> getSoilInfo(@RequestParam("address_code") String address_code) throws Exception{
		
		Map<String, Object> resultMap = new HashMap<>();
		                  System.out.println("!!!!!!!!!!!!");
		                  System.out.println(address_code);
	     StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1390802/SoilEnviron/SoilExam/getSoilExamList"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=u5qnZTsRtR99gpLQQOpnPoUQvKi7VZtNToGrTyAkqCtFyoIHgAbEXhAR6nbI6YHStzltb9iWdDWHxjt2f8O3zA%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("BJD_Code","UTF-8") + "=" + URLEncoder.encode(address_code, "UTF-8")); /*요청 값 포함 일치하는 지번코드에 대한 토양검정 화학성 정보 검색*/
	        urlBuilder.append("&" + URLEncoder.encode("Page_Size","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*요청 값 포함 일치하는 지번코드에 대한 토양검정 화학성 정보 검색*/
	        urlBuilder.append("&" + URLEncoder.encode("Page_No","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*요청 값 포함 일치하는 지번코드에 대한 토양검정 화학성 정보 검색*/

	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        org.json.JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
            String xmlJSONObjString = xmlJSONObj.toString();
 
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<>();
            map = objectMapper.readValue(xmlJSONObjString, new TypeReference<Map<String, Object>>(){});
            Map<String, Object> dataResponse = (Map<String, Object>) map.get("response");
            Map<String, Object> body = (Map<String, Object>) dataResponse.get("body");
            Map<String, Object> items = null;
            List<Map<String, Object>> itemList = null;
 
            int cnt = Integer.parseInt(body.get("Total_Count").toString());
            if(cnt > 0) {            	
            	items = (Map<String, Object>) body.get("items");
            	resultMap.put("data", items);
            }else {
            	resultMap.put("data", "no result");
            }
//            itemList = (List<Map<String, Object>>) items.get("item");
       

		return resultMap;
		
	}
	
	
	// 토양 정보 불러오는 API
		@RequestMapping("getWeatherInfo")
		@ResponseBody
		public Map<String,Object> getWeatherInfo(@RequestParam("address_code") String address_code, @RequestParam("weather_date") String weather_date) throws Exception{
			Map<String, Object> resultMap = new HashMap<>();

			HashMap<String,Object> param = service.selectWeatherInfo(address_code);
			String obsr_Spot_Code = param.get("area_code").toString();
			String obsr_Spot_Nm = param.get("city_name").toString();
			
			LocalDate now = LocalDate.now();
			if(weather_date.equals("")) {
				weather_date = now.toString();
			}
			System.out.println(weather_date);
			
		     StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1390802/AgriWeather/WeatherObsrInfo/InsttWeather/getWeatherTenMinList"); /*URL*/
		        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=u5qnZTsRtR99gpLQQOpnPoUQvKi7VZtNToGrTyAkqCtFyoIHgAbEXhAR6nbI6YHStzltb9iWdDWHxjt2f8O3zA%3D%3D"); /*Service Key*/
		        urlBuilder.append("&" + URLEncoder.encode("time","UTF-8") + "=" + URLEncoder.encode("1300", "UTF-8")); /*요청 값 포함 일치하는 지번코드에 대한 토양검정 화학성 정보 검색*/
		        urlBuilder.append("&" + URLEncoder.encode("date","UTF-8") + "=" + URLEncoder.encode(weather_date, "UTF-8")); /*요청 값 포함 일치하는 지번코드에 대한 토양검정 화학성 정보 검색*/
		        urlBuilder.append("&" + URLEncoder.encode("obsr_Spot_Code","UTF-8") + "=" + URLEncoder.encode(obsr_Spot_Code, "UTF-8")); /*요청 값 포함 일치하는 지번코드에 대한 토양검정 화학성 정보 검색*/
		        urlBuilder.append("&" + URLEncoder.encode("obsr_Spot_Nm","UTF-8") + "=" + URLEncoder.encode(obsr_Spot_Nm, "UTF-8")); /*요청 값 포함 일치하는 지번코드에 대한 토양검정 화학성 정보 검색*/
		        urlBuilder.append("&" + URLEncoder.encode("Page_Size","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*요청 값 포함 일치하는 지번코드에 대한 토양검정 화학성 정보 검색*/
		        urlBuilder.append("&" + URLEncoder.encode("Page_No","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*요청 값 포함 일치하는 지번코드에 대한 토양검정 화학성 정보 검색*/

		        URL url = new URL(urlBuilder.toString());
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestMethod("GET");
		        conn.setRequestProperty("Content-type", "application/json");
		        
		        System.out.println("Response code: " + conn.getResponseCode());
		        BufferedReader rd;
		        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
		            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        } else {
		            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		        }
		        StringBuilder sb = new StringBuilder();
		        String line;
		        while ((line = rd.readLine()) != null) {
		            sb.append(line);
		        }
		        rd.close();
		        conn.disconnect();
		        org.json.JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
	            String xmlJSONObjString = xmlJSONObj.toString();
	 
	            ObjectMapper objectMapper = new ObjectMapper();
	            Map<String, Object> map = new HashMap<>();
	            map = objectMapper.readValue(xmlJSONObjString, new TypeReference<Map<String, Object>>(){});
	            Map<String, Object> dataResponse = (Map<String, Object>) map.get("response");
	            Map<String, Object> body = (Map<String, Object>) dataResponse.get("body");
	            Map<String, Object> items = null;
	            List<Map<String, Object>> itemList = null;
	 
	            Map<String, Object> resultCode = (Map<String, Object>) map.get("Result_Code");

	   
	            System.out.println(sb);
	            if(body != null) {             	
	            	items = (Map<String, Object>) body.get("items");
	            	resultMap.put("data", items);
	            }else {
	    			resultMap.put("data", "no result");	
	            }


			return resultMap;
			
}
		
		
		@RequestMapping("insertWeatherAndSoil")
		@ResponseBody
		public int insertWeatherAndSoil(@RequestParam HashMap<String,Object> param) throws Exception{

			WeatherSoilInfo info = new WeatherSoilInfo();
	
			info.setPlan_id(Integer.parseInt(param.get("plan_id").toString()));			
			info.setSoil(param.get("soil").toString());			
			info.setWeather(param.get("weather").toString());
			info.setComment(param.get("comment").toString());
			
			// 중복확인
			int count = service.checkWeatherSoilInfo(Integer.parseInt(param.get("plan_id").toString()));
			
			int result = 0;
			if(count < 1) {				
				result = service.insertWeatherSoilInfo(info);
			}else {
				result = service.updateWeatherSoilInfo(info);				
			}
					
			return result;
		}
	
	
}