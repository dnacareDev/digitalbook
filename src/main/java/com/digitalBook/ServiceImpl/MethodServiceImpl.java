package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Research;
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
}