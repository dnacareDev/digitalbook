package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Method;
import com.digitalBook.Entity.Research;
import com.digitalBook.Entity.Step;

public interface MethodService
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
	List<Method> SearchMethod(String search_type, String keyword, int offset, int limit);
	
	// 재배 프로토콜 개수 검색
	int SearchMethodCount(String search_type, String keyword);
	
	// 재배 프로토콜 detail
	Method selectMethodDetail(int method_id);
	
	// 재배 프로토콜 step detail list
	List<Step> selectStepDetailList(int method_id);
	
	// 재배 프로토콜 수정
	int updateMethod(Method method);
	
	//step 삭제
	int deleteStep(int step_id);
	
}