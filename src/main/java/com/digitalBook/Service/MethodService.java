package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Research;

public interface MethodService
{
	// 입력단위 조회
	List<Eaches> SelectEaches(String eaches_type);

	// 조사방법 조회
	List<Research> SelectResearch(int division_id);
}