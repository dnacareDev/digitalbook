package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import org.thymeleaf.standard.expression.Each;

import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Reagent;

@Mapper
public interface ReagentMapper {
	
	//시약 검색
	List<Reagent> SearchReagent(@Param("search_type") String search_type, @Param("keyword") String keyword,
			@Param("offset") int offset, @Param("limit") int limit, @Param("user_group") int user_group);
	
	//시약 갯수 검색
	int SearchReagentCount(@Param("search_type") String search_type, @Param("keyword") String keyword, @Param("user_group") int user_group);
	
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
