package com.digitalBook.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Department;
import com.digitalBook.Service.StorageService;

@Controller
@RequestMapping(value = "/data")
// 기초정보 > 실험장소
public class StorageController
{
	@Autowired
	private StorageService service;
	
	// 실험장소 목록
	@RequestMapping(value = "/storage")
	public ModelAndView getStorage(ModelAndView mv)
	{
		List<Department> department = service.SelectDepartment();
		
		mv.addObject("department", department);
		
		mv.setViewName("storage/storage_list");
		
		return mv;
	}
}