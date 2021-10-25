package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Genetic;
import com.digitalBook.Entity.Record;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Entity.User;
import com.digitalBook.Entity.Warehouse;

@Mapper
public interface SeedMapper
{
	//시료 개수 검색
	int SearchSeedCount(@Param("search_type") String search_type, @Param("keyword") String keyword, @Param("user_group") int user_group);
	
	//시료 검색
	List<Seed> SearchSeed(@Param("search_type") String search_type, @Param("keyword") String keyword, @Param("user_group") int user_group, @Param("offset") int offset, @Param("limit") int limit);
	
	//과제 전체 조회
	List<Report> SelectReportList(int user_group);
	
	//사용자 전체 조회
	List<User> SelectUserList(int user_group);
	
	//단위 전체 조회
	List<Eaches> SelectEachesList();
	
	//저장 장소 전체 조회
	List<Warehouse> SelectWarehouseList();
	
	//조사 항목(작목) 전체 조회
	List<Division> SelectDivisionList();
	
	//품종, 유전정보 전체 조회
	List<Genetic> SelectGeneticList(@Param("division_id") int division_id, @Param("genetic_type") int genetic_type);
	
	//시료 등록
	int InsertSeed(Seed seed);
	
	//최근 시료 ID
	String SelectLastSeedCode(int user_group);
	
	//시료 detail list
	List<Seed> SelectSeedDetailList(int report_id);
	
	//시료 수정
	int UpdateSeed(Seed seed);
	
	//시료 삭제
	int DeleteSeed(int seed_id);
	
	//시료 detail
	Seed SelectSeedDetail(int seed_id);
	
	//과제 비연계 등록
	int InsertNonReport(Report report);
	
	//최근 비연계 과제 ID 조회
	String selectLastNonReportCode(int user_group);
	
	//변경 이력 등록
	int insertRecord(Record record);
	
	//시료 변경 이력 조회
	List<Record> selectRecordList(int report_id);
	
	//시료 승인
	int updateSeedStatus(int seed_id);
	
}
