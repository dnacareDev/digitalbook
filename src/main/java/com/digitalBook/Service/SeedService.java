package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Genetic;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Entity.User;
import com.digitalBook.Entity.Warehouse;

public interface SeedService {
	
	//시료 검색
	List<Seed> SearchSeed(String search_type, String keyword, int offset, int limit);
		
	//시료 개수 검색
	int SearchSeedCount(String search_type, String keyword);
	
	//과제 전체 조회
	List<Report> selectReportList();
	
	//사용자 전체 조회
	List<User> selectUserList();
	
	//단위 전체 조회
	List<Eaches> selectEachesList();
	
	//저장 장소 전체 조회
	List<Warehouse> selectWarehouseList();
	
	//조사 항목(작목) 전체 조회
	List<Division> selectDivisionList();
	
	//품종, 유전정보 전체 조회
	List<Genetic> selectGeneticList(int division_id, int genetic_type);
	
	//시료 등록
	int insertSeed(Seed seed);
	
	//최근 시료 ID
	String selectLastSeedCode();
	
	//시료 detail list
	List<Seed> selectSeedDetailList(int report_id);
	
	//시료 수정
	int updateSeed(Seed seed);
	
	//시료 삭제
	int deleteSeed(int seed_id);
	
	//시료 detail
	Seed selectSeedDetail(int seed_id);
	
}