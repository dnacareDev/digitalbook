package com.digitalBook.Controller;

import java.text.SimpleDateFormat;
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
import com.digitalBook.Entity.Record;
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
		
		List<Report> report = service.SelectReportList(prin.getUser_name_k());			// 과제 list
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
		String resultCode = "";
		
		String last_seed_code =service.SelectLastSeedCode(prin.getUser_group());
		String[] strArr = {};
		
		if(last_seed_code != null)
		{
			strArr = last_seed_code.split("-");
			code3 = Integer.parseInt(strArr[2]);
		}
		
		for(int i = 0; i < seeds.size(); i++)
		{
			if(seeds.get(i).getSend_date() == "") {
				seeds.get(i).setSend_date(null);
			}
			if(seeds.get(i).getReceive_date() == "") {
				seeds.get(i).setReceive_date(null);
			}
			
			code3++;
			resultCode = code1+code2+String.format("%05d", code3);
			seeds.get(i).setSeed_code(resultCode);
			seeds.get(i).setUser_group(prin.getUser_group());
			seeds.get(i).setSeed_status(0);
			result[i] = service.InsertSeed(seeds.get(i));
			if(result[i] != 0) {
				Record record = new Record();
				record.setRecord_status(0);
				record.setRecord_type(seeds.get(i).getLast_seed_id());
				record.setRecord_type_code(resultCode);
				service.insertRecord(record);
			}
		}
		
		return result;
	}
	
	// 시료 수정 화면
	@RequestMapping("/seed/modify")
	public ModelAndView SeedModify(ModelAndView mv, Authentication auth, @RequestParam("report_id") int report_id)
	{
		User prin = (User)auth.getPrincipal();
		
		List<Seed> seeds = service.SelectSeedDetailList(report_id);				// 종자(시료) list
		
		List<User> user = service.SelectUserList(prin.getUser_group());			// 사용자 list
		List<Eaches> eaches = service.SelectEachesList();						// 단위 list
		List<Warehouse> warehouse = service.SelectWarehouseList();				// 저장장소 list
		List<Division> division = service.SelectDivisionList();					// 품종, 유전자원명 list
		
		List<Record> record = service.selectRecordList(seeds.get(0).getReport_code());
		
		mv.addObject("seeds", seeds);
		mv.addObject("user", user);
		mv.addObject("eaches", eaches);
		mv.addObject("warehouse", warehouse);
		mv.addObject("division", division);
		mv.addObject("record", record);
		
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
		String resultCode = "";
		
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
				if(seeds.get(i).getSend_date() == "") {
					seeds.get(i).setSend_date(null);
				}
				if(seeds.get(i).getReceive_date() == "") {
					seeds.get(i).setReceive_date(null);
				}
				seeds.get(i).setSeed_status(1);
				updateResult[i] = service.UpdateSeed(seeds.get(i));
				if(updateResult[i] != 0) {
					Record record = new Record();
					record.setRecord_status(1);
					record.setRecord_type(seeds.get(i).getSeed_id());
					record.setRecord_type_code(seeds.get(i).getSeed_code());
					service.insertRecord(record);
				}
			}
			else
			{
				// 해당 시료가 없으면 insert
				if(seeds.get(i).getSend_date() == "") {
					seeds.get(i).setSend_date(null);
				}
				if(seeds.get(i).getReceive_date() == "") {
					seeds.get(i).setReceive_date(null);
				}
				
				code3++;
				resultCode = code1+code2+String.format("%05d", code3);
				seeds.get(i).setSeed_code(resultCode);
				seeds.get(i).setUser_group(prin.getUser_group());
				seeds.get(i).setSeed_status(3);
				
				updateResult[i] = service.InsertSeed(seeds.get(i));
				if(updateResult[i] != 0) {
					Record record = new Record();
					record.setRecord_status(3);
					record.setRecord_type(seeds.get(i).getLast_seed_id());
					record.setRecord_type_code(resultCode);
					service.insertRecord(record);
				}
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
	
	//과제 비연계 등록
	@ResponseBody
	@RequestMapping("/seed/insertReport")
	public String InsertReport(Authentication auth)
	{
		
		Report report = new Report();
		
		int result = 0;
		String returnResult = "";
		
		User prin = (User)auth.getPrincipal();
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		String last_report_code = service.selectLastNonReportCode(prin.getUser_group());
		String code1 = "N" + sdf.format(cal.getTime());//N+년월일
		String code2 = String.format("%02d", prin.getUser_group());//user 그룹
		
		if(last_report_code == null || last_report_code.equals("")) {
			report.setReport_code(code1+code2+"01");
		}else {
			int code3 = Integer.parseInt(last_report_code.substring(11, 13)) + 1;
			report.setReport_code(code1+code2+String.format("%02d", code3));
		}
		
		report.setUser_name("0");
		report.setUser_group(prin.getUser_group());
		result = service.InsertNonReport(report);
		
		if(result != 0) {	//과제 등록 성공 후
			returnResult = report.getReport_code(); //result에 등록된 report_id 값 담기
		}
		
		return returnResult;
	}
	
	//시료 승인
	@ResponseBody
	@RequestMapping("seed/updateStatus")
	public int UpdateStatus(@RequestBody List<Seed> seeds)
	{
		int result = 0;
		
		int updateResult[] = new int[seeds.size()];
		
		for(int i = 0; i < seeds.size(); i++)
		{
			
			updateResult[i] = service.updateSeedStatus(seeds.get(i).getSeed_id());
			if(updateResult[i] != 0) {
				Record record = new Record();
				record.setRecord_status(2);
				record.setRecord_type(seeds.get(i).getSeed_id());
				record.setRecord_type_code(seeds.get(i).getSeed_code());
				service.insertRecord(record);
			}
		}
		
		Boolean isUpdate = IntStream.of(updateResult).noneMatch(x -> x == 0);
		
		if(isUpdate)
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