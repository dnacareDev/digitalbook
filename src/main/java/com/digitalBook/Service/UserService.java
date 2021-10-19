package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.User;

public interface UserService {
	
	//사용자 검색
	List<User> searchUser(String search_type, String keyword, int offset, int limit);
	
	//사용자 개수 검색
	int searchUserCount(String search_type, String keyword);

}
