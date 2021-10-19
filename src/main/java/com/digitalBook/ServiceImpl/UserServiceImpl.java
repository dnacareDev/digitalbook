package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Department;
import com.digitalBook.Entity.User;
import com.digitalBook.Mapper.UserMapper;
import com.digitalBook.Service.UserService;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserMapper mapper;
	
	// 사용자 개수 검색
	@Override
	public int SearchUserCount(String search_type, String keyword)
	{
		return mapper.SearchUserCount(search_type, keyword);
	}
	
	// 사용자 검색
	@Override
	public List<User> SearchUser(String search_type, String keyword, int offset, int limit)
	{
		return mapper.SearchUser(search_type, keyword, offset, limit);
	}

	// 소속실 조회
	@Override
	public List<Department> SelectDepartment(int depart_id, int depart_depth)
	{
		return mapper.SelectDepartment(depart_id, depart_depth);
	}

	// 사용자 등록
	@Override
	public int InsertUser(User user)
	{
		return mapper.InsertUser(user);
	}
}