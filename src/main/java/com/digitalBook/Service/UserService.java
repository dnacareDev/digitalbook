package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Department;
import com.digitalBook.Entity.User;

public interface UserService
{
	// 사용자 개수 검색
	int SearchUserCount(String search_type, String keyword);
	
	// 사용자 검색
	List<User> SearchUser(String search_type, String keyword, int offset, int limit);

	// 소속실 조회
	List<Department> SelectDepartment(int depart_id, int depart_depth);

	// 사용자 등록
	int InsertUser(User user);
}