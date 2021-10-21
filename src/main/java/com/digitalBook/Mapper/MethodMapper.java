package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Method;
import com.digitalBook.Entity.Research;
import com.digitalBook.Entity.Step;

@Mapper
public interface MethodMapper
{
	// 입력단위 조회
	List<Eaches> SelectEaches(String eaches_type);

	// 조사방법 조회
	List<Research> SelectResearch(int division_id);
	
	// 재배 프로토콜 등록
	int InsertMethod(Method method);
	
	// 최근 재배 프로토콜 ID
	String selectLastMethodCode();
	
	// 실험, 재배 단계 등록(step)
	int InsertStep(Step step);
	
	// 재배 프로토콜 검색
	List<Method> SearchMethod(@Param("search_type") String search_type, @Param("keyword") String keyword,
			@Param("offset") int offset, @Param("limit") int limit);
	
	// 재배 프로토콜 개수 검색
	int SearchMethodCount(@Param("search_type") String search_type, @Param("keyword") String keyword);
	
}