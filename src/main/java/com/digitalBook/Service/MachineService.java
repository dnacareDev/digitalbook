package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Machine;

public interface MachineService
{
	// 장비 상세 조회
	Machine SelectMachineDetail(int machine_id);
	
	// 최신 장비 조회
	Machine SelectLastMachine(int user_group);

	// 장비 갯수 검색
	int SearchMachineCount(int search_type, String keyword, int user_group);
	
	// 장비 검색
	List<Machine> SearchMachine(int search_type, String keyword, int offset, int limit, int user_group);
	
	// 장비 등록
	int InsertMachine(Machine machine);

	// 장비 수정
	int UpdateMachine(Machine machine);

	// 장비 삭제
	int DeleteMachine(int machine_id);
}