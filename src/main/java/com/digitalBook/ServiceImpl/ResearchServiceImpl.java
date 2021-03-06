package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Research;
import com.digitalBook.Mapper.ResearchMapper;
import com.digitalBook.Service.ResearchService;

@Service
public class ResearchServiceImpl implements ResearchService
{
	@Autowired
	private ResearchMapper mapper;
	
	// 조사항목 조회
	@Override
	public List<Division> SelectDivision(int division_id, int division_depth)
	{
		return mapper.SelectDivision(division_id, division_depth);
	}

	// 조사방법 상세 조회
	@Override
	public Research SelectResearchDetail(int research_id)
	{
		return mapper.SelectResearchDetail(research_id);
	}

	// 최근 조사방법 조회
	@Override
	public Research SelectLastResearch(int user_group)
	{
		return mapper.SelectLastResearch(user_group);
	}

	// 조사방법 갯수 검색
	@Override
	public int SearchResearchCount(int search_type, String keyword, int user_group)
	{
		return mapper.SearchResearchCount(search_type, keyword, user_group);
	}

	// 조사방법 검색
	@Override
	public List<Research> SearchResearch(int search_type, String keyword, int offset, int limit, int user_group)
	{
		return mapper.SearchResearch(search_type, keyword, offset, limit, user_group);
	}
	
	// 조사방법 등록
	@Override
	public int InsertResearch(Research research)
	{
		return mapper.InsertResearch(research);
	}

	// 조사방법 수정
	@Override
	public int UpdateResearch(int research_id, int division_id, String research_contents)
	{
		return mapper.UpdateResearch(research_id, division_id, research_contents);
	}

	// 조사방법 삭제
	@Override
	public int DeleteResearch(int research_id)
	{
		return mapper.DeleteResearch(research_id);
	}
}