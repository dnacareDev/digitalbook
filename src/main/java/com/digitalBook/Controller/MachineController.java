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

import com.digitalBook.Entity.Machine;
import com.digitalBook.Service.MachineService;

@Controller
@RequestMapping(value = "/data")
//기초정보 > 장비
public class MachineController
{
	@Autowired
	private MachineService service;
	
	// 장비 목록 페이지
	@RequestMapping(value = "/machine")
	public ModelAndView Machine(ModelAndView mv)
	{
		mv.setViewName("machine/machine_list");
		
		return mv;
	}
	
	// 장비 등록 페이지
	@RequestMapping(value = "/machine/insert")
	public ModelAndView MachineInsert(ModelAndView mv)
	{
		mv.setViewName("machine/machine_insert");
		
		return mv;
	}
	
	// 장비 수정 페이지
	@RequestMapping(value = "/machine/modify")
	public ModelAndView MachineModify(ModelAndView mv, @RequestParam("machine_id") int machine_id)
	{
		Machine machine = service.SelectMachineDetail(machine_id);

		mv.addObject("machine", machine);
		
		mv.setViewName("machine/machine_modify");
		
		return mv;
	}
	
	// 장비 검색
	@ResponseBody
	@RequestMapping("/machine/searchMachine")
	public Map<String, Object> SearchMachine(@RequestParam("search_type") int search_type, @RequestParam("keyword") String keyword, @RequestParam("page_num") int page_num, @RequestParam("limit") int limit)
	{
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		int count = service.SearchMachineCount(search_type, keyword);
		
		int offset = (page_num - 1) * limit;
		int end_page = (count + limit - 1) / limit;
		
		List<Machine> machine = service.SearchMachine(search_type, keyword, offset, limit);
		
		result.put("machine", machine);
		result.put("page_num", page_num);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
	
	// 장비 등록
	@ResponseBody
	@RequestMapping("/machine/insertMachine")
	public int InsertMachine(@ModelAttribute Machine machine)
	{
		Machine last_machine = service.SelectLastMachine();

		Calendar cal = Calendar.getInstance();
		
		int now = cal.get(Calendar.YEAR);
		
		if(last_machine == null)
		{
			machine.setMachine_code("eq-" + now + "-00001");
		}
		else
		{
			String[] strArr = last_machine.getMachine_code().split("-");
			
			int code = Integer.parseInt(strArr[2]) + 1;
			machine.setMachine_code("eq-" + now + "-" + String.format("%05d", code));
		}
		
		int result = service.InsertMachine(machine);
		
		return result;
	}
	
	// 장비 수정
	@ResponseBody
	@RequestMapping("/machine/updateMachine")
	public int UpdateMachine(@ModelAttribute Machine machine)
	{
		int result = service.UpdateMachine(machine);
		
		return result;
	}
	
	// 장비 삭제
	@ResponseBody
	@RequestMapping("/machine/deleteMachine")
	public int DeleteMachine(@RequestParam("machine_id") int machine_id)
	{
		int result = service.DeleteMachine(machine_id);
		
		return result;
	}
}