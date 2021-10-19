package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.User;

@Mapper
public interface UserMapper {
	
	//사용자 검색
	List<User> searchUser(@Param("search_type") String search_type, @Param("keyword") String keyword,
			@Param("offset") int offset, @Param("limit") int limit);
	
	//사용자 갯수 검색
	int searchUserCount(@Param("search_type") String search_type, @Param("keyword") String keyword);
	
}
