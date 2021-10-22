package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Reagent;

public interface ReagentService {
	
	//시약 검색
	List<Reagent> SearchReagent(String search_type, String keyword, int offset, int limit, int user_group);
	
	//시약 갯수 검색
	int SearchReagentCount(String search_type, String keyword, int user_group);
	
	//단위 조회
	List<Eaches> selectEaches();
	
	//시약 등록
	int insertReagent(Reagent reagent);
	
	//최근 시약 ID 조회
	String selectLastReagnetCode(int user_group);
	
	//시약 상세조회
	Reagent selectReagentDetail(int reagent_id);
	
	//시약 수정
	int updateReagent(Reagent reagent);
	
	//시약 삭제
	int deleteReagent(int reagent_id);

}
