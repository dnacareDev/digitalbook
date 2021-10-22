package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Method;
import com.digitalBook.Entity.Record;
import com.digitalBook.Entity.Research;
import com.digitalBook.Entity.Step;
import com.digitalBook.Mapper.MethodMapper;
import com.digitalBook.Service.MethodService;

@Service
public class MethodServiceImpl implements MethodService
{
	@Autowired
	private MethodMapper mapper;

	// 입력단위 조회
	@Override
	public List<Eaches> SelectEaches(String eaches_type)
	{
		return mapper.SelectEaches(eaches_type);
	}

	// 조사방법 조회
	@Override
	public List<Research> SelectResearch(int division_id, int user_group)
	{
		return mapper.SelectResearch(division_id, user_group);
	}
	
	// 재배 프로토콜 등록
	@Override
	public int InsertMethod(Method method) {
		
		return mapper.InsertMethod(method);
	}
	
	// 최근 재배 프로토콜 ID
	@Override
	public String selectLastMethodCode(int user_group) {
		
		return mapper.selectLastMethodCode(user_group);
	}
	
	// 실험, 재배 단계 등록(step)
	@Override
	public int InsertStep(Step step) {
		
		return mapper.InsertStep(step);
	}
	
	// 재배 프로토콜 검색
	@Override
	public List<Method> SearchMethod(String search_type, String keyword, int offset, int limit, int user_group) {
		
		return mapper.SearchMethod(search_type, keyword, offset, limit, user_group);
	}
	
	// 재배 프로토콜 개수 검색
	@Override
	public int SearchMethodCount(String search_type, String keyword, int user_group) {
		
		return mapper.SearchMethodCount(search_type, keyword, user_group);
	}
	
	// 재배 프로토콜 detail
	@Override
	public Method selectMethodDetail(int method_id) {
		
		return mapper.selectMethodDetail(method_id);
	}
	
	// 재배 프로토콜 step detail list
	@Override
	public List<Step> selectStepDetailList(int method_id) {
		
		return mapper.selectStepDetailList(method_id);
	}
	
	// 재배 프로토콜 수정
	@Override
	public int updateMethod(Method method) {
		
		return mapper.updateMethod(method);
	}
	
	// step 삭제
	@Override
	public int deleteStep(int step_id) {
		
		return mapper.deleteStep(step_id);
	}
	
	// 재배 프로토콜 삭제
	@Override
	public int deleteMethod(int method_id) {
		
		return mapper.deleteMethod(method_id);
	}
	
	// 변경 이력 등록
	@Override
	public int insertRecord(Record record) {
		
		return mapper.insertRecord(record);
	}
	
	// 프로토콜 변경 이력 조회
	@Override
	public List<Record> selectRecordList(int record_type) {
		
		return mapper.selectRecordList(record_type);
	}
	
	// 프로토콜 승인
	@Override
	public int updateMethodStatus(int method_id) {
		
		return mapper.updateMethodStatus(method_id);
	}
}