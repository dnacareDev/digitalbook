package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Method;
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
	public List<Research> SelectResearch(int division_id)
	{
		return mapper.SelectResearch(division_id);
	}
	
	// 재배 프로토콜 등록
	@Override
	public int InsertMethod(Method method) {
		
		return mapper.InsertMethod(method);
	}
	
	// 최근 재배 프로토콜 ID
	@Override
	public String selectLastMethodCode() {
		
		return mapper.selectLastMethodCode();
	}
	
	// 실험, 재배 단계 등록(step)
	@Override
	public int InsertStep(Step step) {
		
		return mapper.InsertStep(step);
	}
	
	// 재배 프로토콜 검색
	@Override
	public List<Method> SearchMethod(String search_type, String keyword, int offset, int limit) {
		
		return mapper.SearchMethod(search_type, keyword, offset, limit);
	}
	
	// 재배 프로토콜 개수 검색
	@Override
	public int SearchMethodCount(String search_type, String keyword) {
		
		return mapper.SearchMethodCount(search_type, keyword);
	}
}