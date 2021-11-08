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
import com.digitalBook.Entity.Record;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Results;
import com.digitalBook.Entity.Schedule;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Entity.Storage;
import com.digitalBook.Entity.User;

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
	
	//시비량 list
	List<Manure> selectManureList(int plan_id);
	
	//기타 list
	List<Etc> selectEtcList(int plan_id);
	
	//재배계획 수정
	int updatePlan(Plan plan);
	
	//시비량 삭제
	int deleteManure(int manure_id);
	
	//기타요인 삭제
	int deleteEtc(int etc_id);
	
	//시험구 배치 요인 삭제
	int deleteFactor(int factor_id);
	
	//변경 이력 등록
	int insertRecord(Record record);
		
	//재배계획 변경 이력 조회
	List<Record> selectRecordList(int record_type);
	
	//재배계획 승인
	int updatePlanStatus(int plan_id);
	
	//사용자 조회
	List<User> selectUserList(int user_group);
	
	//담당자 등록
	int insertSchedule(Schedule schedule);
	
	//담당자 리스트 조회
	List<Schedule> selectScheduleList(int plan_id);
	
	//담당자 삭제
	int deleteSchedule(int sch_id);
	
	//포장 등록
	int InsertStorage(Storage storage);
	
	//장소 상태 변경
	int updateStorageStatus(int storage_id);
	
	//plan method 가져오기
	List<Method> selectPlanMethodList(int[] arr);
	
	//결과입력 등록 foreach
	int insertResults(@Param("results") List<Results> results);
	
	//결과입력 데이터 조회
	List<Results> selectResultsList(int plan_id);
	
	//담당자 변경시 plan status 수정
	int updatePlanStatus1(int plan_id);
	
	//결과입력 삭제
	int deleteResults(int[] arr);
	
}
