package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.User;
import com.digitalBook.Mapper.UserMapper;
import com.digitalBook.Service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper mapper;
	
	//사용자 검색
	@Override
	public List<User> searchUser(String search_type, String keyword, int offset, int limit) {
		
		return mapper.searchUser(search_type, keyword, offset, limit);
	}
	
	//사용자 개수 검색
	@Override
	public int searchUserCount(String search_type, String keyword) {
		
		return mapper.searchUserCount(search_type, keyword);
	}

}
