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

import com.digitalBook.Entity.User;
import com.digitalBook.Service.UserService;

//기초정보 > 사용자
@Controller
@RequestMapping(value = "/data")
public class UserController
{
	
	@Autowired
	private UserService service;
	
	//사용자 목록
	@RequestMapping(value = "user")
	public ModelAndView UserList(ModelAndView mv)
	{
		mv.setViewName("user/user_list");
		
		return mv;
	}
	
	//사용자 검색
	@ResponseBody
	@RequestMapping("/user/searchUser")
	public Map<String, Object> searchUser(@RequestParam(name = "search_type", required = false) String search_type,
									@RequestParam(name = "keyword", required = false) String keyword,
									@RequestParam("page_num") int page_num,
									@RequestParam("limit") int limit){
		
		Map<String, Object> result = new LinkedHashMap<>();
		
		int count = service.searchUserCount(search_type, keyword);
		
		int offset = (page_num - 1) * limit;
		int end_page = (count + limit - 1) / limit;
		
		List<User> user = service.searchUser(search_type, keyword, offset, limit);
		
		result.put("user", user);
		result.put("page_num", page_num);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
}