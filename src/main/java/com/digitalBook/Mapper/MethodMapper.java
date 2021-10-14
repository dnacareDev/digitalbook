package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Research;

@Mapper
public interface MethodMapper
{
	// 입력단위 조회
	List<Eaches> SelectEaches(String eaches_type);

	// 조사방법 조회
	List<Research> SelectResearch(int division_id);
}