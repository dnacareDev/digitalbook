package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Research;

public interface ResearchService
{
	// 조사항목 조회
	List<Division> SelectDivision(int division_id, int division_depth);

	// 조사방법 상세 조회
	Research SelectResearchDetail(int research_id);

	// 최근 조사방법 조회
	Research SelectLastResearch();

	// 조사방법 갯수 검색
	int SearchResearchCount(int search_type, String keyword);

	// 조사방법 검색
	List<Research> SearchResearch(int search_type, String keyword, int offset, int limit);
	
	// 조사방법 등록
	int InsertResearch(Research research);

	// 조사방법 수정
	int UpdateResearch(int research_id, int division_id, String research_contents);

	// 조사방법 삭제
	int DeleteResearch(int research_id);
}