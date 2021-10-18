package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Genetic;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Entity.User;
import com.digitalBook.Entity.Warehouse;

@Mapper
public interface SeedMapper {
	
	//시료 검색
	List<Seed> SearchSeed(@Param("search_type") String search_type, @Param("keyword") String keyword,
			@Param("offset") int offset, @Param("limit") int limit);
	
	//시료 개수 검색
	int SearchSeedCount(@Param("search_type") String search_type, @Param("keyword") String keyword);
	
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
	List<Genetic> selectGeneticList(@Param("division_id") int division_id, @Param("genetic_type") int genetic_type);
	
	//시료 등록
	int insertSeed(Seed seed);
	
	//최근 시약 ID
	String selectLastSeedCode();
	
	//시료 detail list
	List<Seed> selectSeedDetailList(int report_id);
	
}
