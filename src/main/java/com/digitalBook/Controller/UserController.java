package com.digitalBook.Controller;

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
import com.digitalBook.Entity.User;
import com.digitalBook.Service.UserService;

//기초정보 > 사용자
@Controller
@RequestMapping("/data")
public class UserController
{
	@Autowired
	private UserService service;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	// 사용자 목록
	@RequestMapping("/user")
	public ModelAndView UserList(ModelAndView mv)
	{
		mv.setViewName("user/user_list");
		
		return mv;
	}
	
	// 사용자 등록 페이지
	@RequestMapping("/user/insert")
	public ModelAndView UserInsert(ModelAndView mv)
	{
		mv.setViewName("user/user_insert");
		
		return mv;
	}
	
	// 사용자 검색
	@ResponseBody
	@RequestMapping("/user/searchUser")
	public Map<String, Object> SearchUser(@RequestParam(name = "search_type", required = false) String search_type, @RequestParam(name = "keyword", required = false) String keyword, @RequestParam("page_num") int page_num, @RequestParam("limit") int limit)
	{
		Map<String, Object> result = new LinkedHashMap<>();
		
		int count = service.SearchUserCount(search_type, keyword);
		
		int offset = (page_num - 1) * limit;
		int end_page = (count + limit - 1) / limit;
		
		List<User> user = service.SearchUser(search_type, keyword, offset, limit);
		
		result.put("user", user);
		result.put("page_num", page_num);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
	
	// 소속실 조회
	@ResponseBody
	@RequestMapping("/user/selectDepartment")
	public List<Department> SelectDepartment(@RequestParam("depart_id") int depart_id, @RequestParam("depart_depth") int depart_depth)
	{
		List<Department> department = service.SelectDepartment(depart_id, depart_depth);
		
		return department;
	}
	
	// 사용자 등록
	@ResponseBody
	@RequestMapping("/user/insertUser")
	public int InsertUser(@ModelAttribute User user)
	{
		
		int result = service.InsertUser(user);
		
		return result;
	}
	
	// 사용자 수정 화면
	@RequestMapping("/user/modify")
	public ModelAndView UserModify(ModelAndView mv, @RequestParam(name = "user_id", required = true) int user_id) {
		
		User user = service.SelectUserDetail(user_id);
		
		List<Department> d1 = service.SelectDepartment(0, 0);
		List<Department> d2 = service.SelectDepartment(user.getD1_id(), 1);
		List<Department> d3 = service.SelectDepartment(user.getD2_id(), 2);
		List<Department> d4 = service.SelectDepartment(user.getD3_id(), 3);
		
		mv.addObject("user", user);
		mv.addObject("d1", d1);
		mv.addObject("d2", d2);
		mv.addObject("d3", d3);
		mv.addObject("d4", d4);
		
		mv.setViewName("user/user_modify");
		
		return mv;
	}
	
	// 사용자 수정
	@ResponseBody
	@RequestMapping("/user/updateUser")
	public int UpdateUser(@ModelAttribute User user) 
	{
		
		int result = service.UpdateUser(user);
		
		return result;
	}
	
	// 사용자 삭제
	@ResponseBody
	@RequestMapping("/user/deleteUser")
	public int DeleteUser(@RequestParam(name = "user_id", required = true) int user_id) 
	{
		
		int result = service.DeleteUser(user_id);
		
		return result;
	}
	
}