package com.digitalBook.Controller;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Material;
import com.digitalBook.Entity.User;
import com.digitalBook.Service.MaterialService;

@Controller
@RequestMapping(value = "/data")
//기초정보 > 기타(농자재)
public class MaterialController {
	
	@Autowired
	private MaterialService service;
	
	//기타(농자재) 목록
	@RequestMapping(value = "/material")
	public ModelAndView material(ModelAndView mv) {
		
		mv.setViewName("material/material_list");
		
		return mv;
	}
	
	//기타(농자재) 등록화면
	@RequestMapping(value = "/material/insert")
	public ModelAndView materialInsert(ModelAndView mv) {
		
		mv.setViewName("material/material_insert");
		
		return mv;
	}
	
	//농자재 검색
	@ResponseBody
	@RequestMapping("/material/searchMaterial")
	public Map<String, Object> searchMaterial(Authentication auth,
											@RequestParam(name = "search_type", required = false) String search_type,
											@RequestParam(name = "keyword", required = false) String keyword,
											@RequestParam("page_num") int page_num,
											@RequestParam("limit") int limit){
		
		Map<String, Object> result = new LinkedHashMap<>();
		
		User prin = (User)auth.getPrincipal();
		
		int user_group = prin.getUser_group();
		
		int count = service.SearchMaterialCount(search_type, keyword, user_group);
		
		int offset = (page_num - 1) * limit;
		int end_page = (count + limit - 1) / limit;
		
		List<Material> material = service.SearchMaterial(search_type, keyword, offset, limit, user_group);
		
		result.put("material", material);
		result.put("page_num", page_num);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		
		return result;
	}
	
	//농자재 등록
	@ResponseBody
	@RequestMapping("/material/insertMaterial")
	public int insertMaterial(Authentication auth, 
							@RequestParam(name = "material_name", required = true) String material_name) {
		
		User prin = (User)auth.getPrincipal();
		
		Material material = new Material();
		
		Calendar cal = Calendar.getInstance();
		String last_material_code = service.selectLastMeterialCode(prin.getUser_group());
		String code1 = "ms-";
		String code2 = String.valueOf(cal.get(Calendar.YEAR))+"-";
		
		if(last_material_code == null || last_material_code.equals("")) {
			material.setMaterial_code(code1+code2+"00001");
		}else {
			String[] strArr = last_material_code.split("-");
			
			int code3 = Integer.parseInt(strArr[2]) + 1;
			material.setMaterial_code(code1+code2+String.format("%05d", code3));
		}//end else
		
		material.setUser_id(prin.getUser_id());
		material.setUser_group(prin.getUser_group());
		material.setMaterial_name(material_name);
		int result = service.insertMaterial(material);
		
		return result;
	}
	
	//농자재 수정 화면
	@RequestMapping("/material/modify")
	public ModelAndView materialModify(ModelAndView mv, @RequestParam(name = "material_id", required = true) int material_id) {
		
		Material material = service.selectMaterialDetail(material_id);
		
		mv.addObject("material", material);
		
		mv.setViewName("material/material_modify");
		
		return mv;
	}
	
	//농자재 수정
	@ResponseBody
	@RequestMapping("/material/updateMaterial")
	public int updateMaterial(@RequestParam(name = "material_id", required = true) int material_id,
							@RequestParam(name = "material_name", required = true) String material_name) {
		
		Material material = service.selectMaterialDetail(material_id);
		material.setMaterial_name(material_name);
		
		int result = service.updateMaterial(material);
		
		return result;
	}
	
	//농자재 삭제
	@ResponseBody
	@RequestMapping("/material/deleteMaterial")
	public int deleteMaterial(@RequestParam(name = "material_id", required = true) int material_id) {
		
		int result = service.deleteMaterial(material_id);
		
		return result;
	}
	
	//엑셀 다운로드
	@RequestMapping(value = "/material/exceldownload", produces = "application/vnd.ms-excel")
	public String ResearchExceldownload(Authentication auth, Model model) 
	{
		
		User prin = (User)auth.getPrincipal();
		model.addAttribute("user_group", prin.getUser_group());
		
		return "materialExcelView";
	}
	
}
