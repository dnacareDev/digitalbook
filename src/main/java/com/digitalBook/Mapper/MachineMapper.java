package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Machine;

@Mapper
public interface MachineMapper
{
	// 장비 상세 조회
	Machine SelectMachineDetail(int machine_id);
	
	// 최신 장비 조회
	Machine SelectLastMachine();

	// 장비 갯수 검색
	int SearchMachineCount(@Param("search_type") int search_type, @Param("keyword") String keyword);
	
	// 장비 검색
	List<Machine> SearchMachine(@Param("search_type") int search_type, @Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);
	
	// 장비 등록
	int InsertMachine(Machine machine);

	// 장비 수정
	int UpdateMachine(Machine machine);

	// 장비 삭제
	int DeleteMachine(int machine_id);
}