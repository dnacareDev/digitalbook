package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Department;
import com.digitalBook.Entity.User;

@Mapper
public interface UserMapper
{
	// 사용자 갯수 검색
	int SearchUserCount(@Param("search_type") String search_type, @Param("keyword") String keyword);
	
	// 사용자 검색
	List<User> SearchUser(@Param("search_type") String search_type, @Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

	// 소속실 조회
	List<Department> SelectDepartment(@Param("depart_id") int depart_id, @Param("depart_depth") int depart_depth);

	// 사용자 등록
	int InsertUser(User user);
	
	// 사용자 상세 조회
	User SelectUserDetail(int user_id);
	
	// 사용자 수정
	int UpdateUser(User user);
	
	// 사용자 삭제
	int DeleteUser(int user_id);
	
	//엑셀 다운로드할 사용자 list
	List<User> selectUserExcelList();
	
}
