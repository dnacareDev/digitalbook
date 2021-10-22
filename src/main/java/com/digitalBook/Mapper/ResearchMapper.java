package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Research;

@Mapper
public interface ResearchMapper
{
	// 조사항목 조회
	List<Division> SelectDivision(@Param("division_id") int division_id, @Param("division_depth") int division_depth);

	// 조사방법 상세 조회
	Research SelectResearchDetail(int research_id);

	// 최근 조사방법 조회
	Research SelectLastResearch(int user_group);

	// 조사방법 갯수 검색
	int SearchResearchCount(@Param("search_type") int search_type, @Param("keyword") String keyword, @Param("user_group") int user_group);

	// 조사방법 검색
	List<Research> SearchResearch(@Param("search_type") int search_type, @Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit, @Param("user_group") int user_group);
	
	// 조사방법 등록
	int InsertResearch(Research research);

	// 조사방법 수정
	int UpdateResearch(@Param("research_id") int research_id, @Param("division_id") int division_id, @Param("research_contents") String research_contents);

	// 조사방법 삭제
	int DeleteResearch(int research_id);
}