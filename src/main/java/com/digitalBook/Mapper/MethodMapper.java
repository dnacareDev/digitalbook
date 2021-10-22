package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Method;
import com.digitalBook.Entity.Record;
import com.digitalBook.Entity.Research;
import com.digitalBook.Entity.Step;

@Mapper
public interface MethodMapper
{
	// 입력단위 조회
	List<Eaches> SelectEaches(String eaches_type);

	// 조사방법 조회
	List<Research> SelectResearch(@Param("division_id") int division_id, @Param("user_group") int user_group);
	
	// 재배 프로토콜 등록
	int InsertMethod(Method method);
	
	// 최근 재배 프로토콜 ID
	String selectLastMethodCode(int user_group);
	
	// 실험, 재배 단계 등록(step)
	int InsertStep(Step step);
	
	// 재배 프로토콜 검색
	List<Method> SearchMethod(@Param("search_type") String search_type, @Param("keyword") String keyword,
			@Param("offset") int offset, @Param("limit") int limit, @Param("user_group") int user_group);
	
	// 재배 프로토콜 개수 검색
	int SearchMethodCount(@Param("search_type") String search_type, @Param("keyword") String keyword, @Param("user_group") int user_group);
	
	// 재배 프로토콜 detail
	Method selectMethodDetail(int method_id);
	
	// 재배 프로토콜 step detail list
	List<Step> selectStepDetailList(int method_id);
	
	// 재배 프로토콜 수정
	int updateMethod(Method method);
	
	// step 삭제
	int deleteStep(int step_id);
	
	// 재배 프로토콜 삭제
	int deleteMethod(int method_id);
	
	// 변경 이력 등록
	int insertRecord(Record record);
	
	// 프로토콜 변경 이력 조회
	List<Record> selectRecordList(int record_type);
	
	// 프로토콜 승인
	int updateMethodStatus(int method_id);
	
}