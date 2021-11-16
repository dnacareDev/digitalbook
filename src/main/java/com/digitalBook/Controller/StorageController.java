package com.digitalBook.Controller;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Department;
import com.digitalBook.Entity.Storage;
import com.digitalBook.Service.StorageService;

@Controller
@RequestMapping("/data")
// 기초정보 > 실험장소
public class StorageController
{
	@Autowired
	private StorageService service;
	
	// 실험장소 목록
	@RequestMapping("/storage")
	public ModelAndView Storage(ModelAndView mv)
	{
		List<Department> department = service.SelectDepartment();
		
		mv.addObject("department", department);
		
		mv.setViewName("storage/storage_list");
		
		return mv;
	}
	
	// 실험장소 검색
	@ResponseBody
	@RequestMapping("/storage/searchStorage")
	public Map<String, Object> SearchStorage(@RequestParam("page_num") int page_num)
	{
		Map<String, Object> result = new LinkedHashMap<String, Object>();;
		
		int count = service.SelectStorageCount();
		
		int limit = 10;
		int offset = (page_num - 1) * limit;
		int start_page = ((page_num - 1) / 10) * 10 + 1;
		int end_page = (count + limit - 1) / limit;
		
		List<Storage> storage = service.SearchStorage(offset, limit);
		
		result.put("storage", storage);
		result.put("page_num", page_num);
		result.put("start_page", start_page);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
	
	// 실험장소 등록
	@RequestMapping("/storage/insertStorage")
	public ModelAndView InsertStorage(ModelAndView mv, @ModelAttribute Storage storage)
	{
		Calendar cal = Calendar.getInstance();
		
		int now = cal.get(Calendar.YEAR);
		
		Storage last_storage = service.SelectLastStorage();
		
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
		
		int result = service.InsertStorage(storage);
		
		mv.setViewName("redirect:/data/storage");
		
		return mv;
	}
}