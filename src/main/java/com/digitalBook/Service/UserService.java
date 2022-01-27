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
	
	// 사용자 상세 조회
	User SelectUserDetail(int user_id);
	
	// 사용자 수정
	int UpdateUser(User user);
	
	// 사용자 삭제
	int DeleteUser(int user_id);

	int checkId(String id);
	
}