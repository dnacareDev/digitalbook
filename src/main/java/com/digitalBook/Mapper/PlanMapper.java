package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Etc;
import com.digitalBook.Entity.Factor;
import com.digitalBook.Entity.Fertilizer;
import com.digitalBook.Entity.Manure;
import com.digitalBook.Entity.Method;
import com.digitalBook.Entity.Plan;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;

@Mapper
public interface PlanMapper {
	
	//종자(시료) 등록된 과제 list
	List<Report> selectReportList();
	
	//시험재료(seed) 조회 list
	List<Seed> selectSeedList(@Param("user_group") int user_group, @Param("report_code") String report_code);
	
	//비료 조회 list
	List<Fertilizer> selectFertilizerList(@Param("fert_depth") int fert_depth, @Param("fert_id") int fert_id);
	
	//조사항목 method 조회
	List<Method> selectMethodList(int user_group);
	
	//재배 계획 등록
	int insertPlan(Plan plan);
	
	//최근 재배 계획 ID 조회
	String selectLastPlanCode(int user_group);
	
	//시험구배치 요인 등록
	int insertFactor(Factor factor);
	
	//기타요인 등록
	int insertEtc(Etc etc);
	
	//시비량 등록
	int insertManure(Manure manure);
	
	//재배 프로토콜 검색
	List<Plan> SearchPlan(@Param("search_type") String search_type, @Param("keyword") String keyword,
		@Param("offset") int offset, @Param("limit") int limit, @Param("user_group") int user_group);
		
	//재배 프로토콜 개수 검색
	int SearchPlanCount(@Param("search_type") String search_type, @Param("keyword") String keyword, @Param("user_group") int user_group);
	
	//재배 계획 Detail
	Plan selectPlanDetail(int plan_id);
	
	//시험구배치 요인 list
	List<Factor> selectFactorList(int plan_id);
	
}
