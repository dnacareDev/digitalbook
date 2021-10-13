package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Reagent;
import com.digitalBook.Mapper.ReagentMapper;
import com.digitalBook.Service.ReagentService;

@Service
public class ReagentServiceImpl implements ReagentService {
	
	@Autowired
	private ReagentMapper mapper;
	
	//시약 검색
	@Override
	public List<Reagent> SearchReagent(String search_type, String keyword, int offset, int limit) {
		
		return mapper.SearchReagent(search_type, keyword, offset, limit);
	}
	
	//시약 갯수 검색
	@Override
	public int SearchReagentCount(String search_type, String keyword) {
		
		return mapper.SearchReagentCount(search_type, keyword);
	}
	
	//단위 조회
	@Override
	public List<Eaches> selectEaches() {
		
		return mapper.selectEaches();
	}
	
	//시약 등록
	@Override
	public int insertReagent(Reagent reagent) {
		
		return mapper.insertReagent(reagent);
	}
	
	//최근 시약 ID
	@Override
	public String selectLastReagnetCode() {
		
		return mapper.selectLastReagnetCode();
	}
	
	//시약 상세조회
	@Override
	public Reagent selectReagentDetail(int reagent_id) {
		
		return mapper.selectReagentDetail(reagent_id);
	}
	
	//시약 수정
	@Override
	public int updateReagent(Reagent reagent) {
		
		return mapper.updateReagent(reagent);
	}
	
	//시약 삭제
	@Override
	public int deleteReagent(int reagent_id) {
		
		return mapper.deleteReagent(reagent_id);
	}

}
