package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Etc;
import com.digitalBook.Entity.Factor;
import com.digitalBook.Entity.Fertilizer;
import com.digitalBook.Entity.Manure;
import com.digitalBook.Entity.Method;
import com.digitalBook.Entity.Plan;
import com.digitalBook.Entity.Record;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Mapper.PlanMapper;
import com.digitalBook.Service.PlanService;

@Service
public class PlanServiceImpl implements PlanService {
	
	@Autowired
	private PlanMapper mapper;
	
	//종자(시료) 등록된 과제 list
	@Override
	public List<Report> selectReportList() {
		
		return mapper.selectReportList();
	}
	
	//시험재료(seed) 조회 list
	@Override
	public List<Seed> selectSeedList(int user_group, String report_code) {
		
		return mapper.selectSeedList(user_group, report_code);
	}
	
	//비료 조회 list
	@Override
	public List<Fertilizer> selectFertilizerList(int fert_depth, int fert_id) {
		
		return mapper.selectFertilizerList(fert_depth, fert_id);
	}
	
	//조사항목 method 조회
	@Override
	public List<Method> selectMethodList(int user_group) {
		
		return mapper.selectMethodList(user_group);
	}
	
	//재배계획 등록
	@Override
	public int insertPlan(Plan plan) {
		
		return mapper.insertPlan(plan);
	}
	
	//최근 재배계획 ID 조회
	@Override
	public String selectLastPlanCode(int user_group) {
		
		return mapper.selectLastPlanCode(user_group);
	}
	
	//시험구 배치 요인 등록
	@Override
	public int insertFactor(Factor factor) {
		
		return mapper.insertFactor(factor);
	}
	
	//기타 요인 등록
	@Override
	public int insertEtc(Etc etc) {
		
		return mapper.insertEtc(etc);
	}
	
	//시비량 등록
	@Override
	public int insertManure(Manure manure) {
		
		return mapper.insertManure(manure);
	}
	
	//재배 계획 검색
	@Override
	public List<Plan> SearchPlan(String search_type, String keyword, int offset, int limit, int user_group) {
		
		return mapper.SearchPlan(search_type, keyword, offset, limit, user_group);
	}
	
	//재배 계획 검색 개수
	@Override
	public int SearchPlanCount(String search_type, String keyword, int user_group) {
		
		return mapper.SearchPlanCount(search_type, keyword, user_group);
	}
	
	//재배 계획 detail
	@Override
	public Plan selectPlanDetail(int plan_id) {
		
		return mapper.selectPlanDetail(plan_id);
	}
	
	//시험구배치 요인 list
	@Override
	public List<Factor> selectFactorList(int plan_id) {
		
		return mapper.selectFactorList(plan_id);
	}
	
	//시비량 list
	@Override
	public List<Manure> selectManureList(int plan_id) {
		
		return mapper.selectManureList(plan_id);
	}
	
	//기타 list
	@Override
	public List<Etc> selectEtcList(int plan_id) {
		
		return mapper.selectEtcList(plan_id);
	}
	
	//재배계획 수정
	@Override
	public int updatePlan(Plan plan) {
		
		return mapper.updatePlan(plan);
	}
	
	//시비량 삭제
	@Override
	public int deleteManure(int manure_id) {
		
		return mapper.deleteManure(manure_id);
	}
	
	//기타요인 삭제
	@Override
	public int deleteEtc(int etc_id) {
		
		return mapper.deleteEtc(etc_id);
	}
	
	//시험구 배치 요인 삭제
	@Override
	public int deleteFactor(int factor_id) {
		
		return mapper.deleteFactor(factor_id);
	}
	
	//변경 이력 등록
	@Override
	public int insertRecord(Record record) {
		
		return mapper.insertRecord(record);
	}

	//재배계획 변경 이력 조회
	@Override
	public List<Record> selectRecordList(int record_type) {
		
		return mapper.selectRecordList(record_type);
	}

}
