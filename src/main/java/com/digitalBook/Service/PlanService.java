package com.digitalBook.Service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Etc;
import com.digitalBook.Entity.Factor;
import com.digitalBook.Entity.Fertilizer;
import com.digitalBook.Entity.Manure;
import com.digitalBook.Entity.Method;
import com.digitalBook.Entity.Plan;
import com.digitalBook.Entity.Record;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.ResultPlan;
import com.digitalBook.Entity.Results;
import com.digitalBook.Entity.Schedule;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Entity.Segment;
import com.digitalBook.Entity.SegmentInfo;
import com.digitalBook.Entity.Storage;
import com.digitalBook.Entity.User;
import com.digitalBook.Entity.WeatherSoilInfo;

public interface PlanService {
	
	//종자(시료) 등록된 과제 list
	List<Report> selectReportList(String user_name);
	
	//시험재료(seed) 조회 list
	List<Seed> selectSeedList(int user_group, String report_code);
	
	//비료 조회 list
	List<Fertilizer> selectFertilizerList(int fert_depth, int fert_id);
	
	//조사항목 method 조회
	List<Method> selectMethodList(int user_group, int user_id);
	
	//재배계획 등록
	int insertPlan(Plan plan);
	
	//최근 재배계획 ID 조회
	String selectLastPlanCode(int user_group);
	
	//시험구배치 요인 등록
	int insertFactor(Factor factor);
	
	//기타 요인 등록
	int insertEtc(Etc etc);
	
	//시비량 등록
	int insertManure(Manure manure);
	
	//재배 계획 검색
	List<Plan> SearchPlan(String search_type, String keyword, int offset, int limit, int user_group);
	
	//재배 계획 검색 개수
	int SearchPlanCount(String search_type, String keyword, int user_group);
	
	//재배 계획 Detail
	Plan selectPlanDetail(int plan_id);
	
	//시험구배치 요인 list
	List<Factor> selectFactorList(int plan_id);
	
	//시비량 ist
	List<Manure> selectManureList(int plan_id);
	
	//기타 list
	List<Etc> selectEtcList(int plan_id);
	
	//재배계획 수정
	int updatePlan(Plan plan);
	
	//시비량 삭제
	int deleteManure(int manure_id);
	
	//기타요인 삭제
	int deleteEtc(int etc_id);
	
	//시험구배치 요인 삭제
	int deleteFactor(int factor_id);
	
	//변경 이력 등록
	int insertRecord(Record record);
		
	//재배계획 변경 이력 조회
	List<Record> selectRecordList(int record_type);
	
	//재배계획 승인
	int updatePlanStatus(int plan_id);

	int insertResultsPlan(ResultPlan results);
	
	int updateResultPlan(ResultPlan results);
	
	//사용자 전체 조회
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
	int updateStorageStatus(int storage_id, int storage_status);
	
	//plan method 가져오기
	List<Method> selectPlanMethodList(int[] arr);
	
	//결과입력 등록 foreach
	int insertResults(List<Results> results);
	
	//결과입력 데이터 조회
	List<Results> selectResultsList(int plan_id);
	
	//담당자 변경시 plan status 수정
	int updatePlanStatus1(int plan_id);
	
	//결과입력 삭제
	int deleteResults(int[] arr);
	
	//구획 등록
	int insertSegment(List<Segment> segment);
	
	//구획 조회
	List<Segment> selectSegmentList(int plan_id);
	
	ResultPlan selectResultPlanOne(int plan_id);
	
	//구획정보 등록
	int insertSegmentInfo(List<SegmentInfo> segmentInfo);
	
	//구획정보 조회
	List<SegmentInfo> selectSegmentInfoList(int plan_id);
	
	//구획정보 삭제
	int deleteSegmentInfo(int[] arr);
	
	//구획 삭제
	int deleteSegment(int[] arr);
	
	//재배 계획 검색
	List<Plan> SearchResultPlan(String search_type, String keyword, int offset, int limit, int user_group, int plan_step);
	
	//재배 계획 검색 개수
	int SearchResultPlanCount(String search_type, String keyword, int user_group, int plan_step);
	
	// 장소 갯수 조회
	int SelectStorageCount(int user_id);
	
	// 장소 검색
	List<Storage> SearchStorage(int offset, int limit, int user_id);
	
	//샘플 과제 detail
	Report selectSampleReportDetail(String report_code);
	
	//샘플 구획 detail
	Segment selectSampleSegmentDetail(int segment_id);

	String selectStroageAddress(int plan_id);

	long selectAddressCode(String address_name);

	HashMap<String, Object> selectWeatherInfo(String address_name);

	int insertWeatherSoilInfo(WeatherSoilInfo info);

	int updateWeatherSoilInfo(WeatherSoilInfo info);

	int checkWeatherSoilInfo(int parseInt);

	int checkResultsPlan(int plan_id);

	int updateResultsPlan(ResultPlan results);
	
}
