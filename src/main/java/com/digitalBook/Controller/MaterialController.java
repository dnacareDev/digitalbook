package com.digitalBook.Controller;

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

import com.digitalBook.Entity.Material;
import com.digitalBook.Service.MaterialService;

@Controller
@RequestMapping(value = "/data")
//기초정보 > 기타(농자재)
public class MaterialController {
	
	@Autowired
	private MaterialService service;
	
	//기타(농자재) 목록
	@GetMapping(value = "/material")
	public ModelAndView getMaterial(ModelAndView mv) {
		
		mv.setViewName("material/material_list");
		
		return mv;
	}
	
	//기타(농자재) 등록화면
	@GetMapping(value = "/material/insert")
	public ModelAndView getMaterialInsert(ModelAndView mv) {
		
		mv.setViewName("material/material_insert");
		
		return mv;
	}
	
	//농자재 검색
	@ResponseBody
	@RequestMapping("/material/searchMaterial")
	public Map<String, Object> searchMaterial(@RequestParam(name = "search_type", required = false) String search_type,
											@RequestParam(name = "keyword", required = false) String keyword,
											@RequestParam("page_num") int page_num,
											@RequestParam("limit") int limit){
		
		Map<String, Object> result = new LinkedHashMap<>();
		
		int count = service.SearchMaterialCount(search_type, keyword);
		
		int offset = (page_num - 1) * limit;
		int end_page = (count + limit - 1) / limit;
		
		List<Material> material = service.SearchMaterial(search_type, keyword, offset, limit);
		
		result.put("material", material);
		result.put("page_num", page_num);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		
		return result;
	}
	
	//농자재 등록
	@ResponseBody
	@RequestMapping("/material/insertMaterial")
	public int insertMaterial(@RequestParam(name = "material_name", required = true) String material_name) {
		
		int result = 0;
		
		Material material = new Material();
		String last_material_code = service.selectLastMeterialCode();
		
		if(last_material_code == null || last_material_code.equals("")) {
			material.setMaterial_code("ms-o-002-00001");
		}else {
			String[] strArr = last_material_code.split("-");
			
			int code = Integer.parseInt(strArr[3]) + 1;
			material.setMaterial_code("ms-o-002-" + String.format("%05d", code));
		}//end else
		
		material.setMaterial_name(material_name);
		result = service.insertMaterial(material);
		
		return result;
	}
	
}
