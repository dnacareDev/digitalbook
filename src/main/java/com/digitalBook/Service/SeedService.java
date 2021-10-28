package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Genetic;
import com.digitalBook.Entity.Record;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Entity.User;
import com.digitalBook.Entity.Warehouse;

public interface SeedService
{
	// 시료 개수 검색
	int SearchSeedCount(String search_type, String keyword, int user_group);
	
	// 시료 검색
	List<Seed> SearchSeed(String search_type, String keyword, int user_group, int offset, int limit);
	
	// 과제 전체 조회
	List<Report> SelectReportList(String user_name);
	
	// 사용자 전체 조회
	List<User> SelectUserList(int user_group);
	
	// 단위 전체 조회
	List<Eaches> SelectEachesList();
	
	// 저장 장소 전체 조회
	List<Warehouse> SelectWarehouseList();
	
	// 조사 항목(작목) 전체 조회
	List<Division> SelectDivisionList();
	
	// 품종, 유전정보 전체 조회
	List<Genetic> SelectGeneticList(int division_id, int genetic_type);
	
	// 시료 등록
	int InsertSeed(Seed seed);
	
	// 최근 시료 ID
	String SelectLastSeedCode(int user_group);
	
	// 시료 detail list
	List<Seed> SelectSeedDetailList(int report_id);
	
	// 시료 수정
	int UpdateSeed(Seed seed);
	
	// 시료 삭제
	int DeleteSeed(int seed_id);
	
	// 시료 detail
	Seed SelectSeedDetail(int seed_id);
	
	//과제 비연계 등록
	int InsertNonReport(Report report);
		
	//최근 비연계 과제 ID 조회
	String selectLastNonReportCode(int user_group);
	
	//변경 이력 등록
	int insertRecord(Record record);
		
	//시료 변경 이력 조회
	List<Record> selectRecordList(String report_code);
		
	//시료 승인
	int updateSeedStatus(int seed_id);
}