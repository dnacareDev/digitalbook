package com.digitalBook.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Fertilizer;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Mapper.PlanMapper;

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
	public List<Seed> selectSeedList(int user_group, int report_id) {
		
		return mapper.selectSeedList(user_group, report_id);
	}
	
	//비료 조회 list
	@Override
	public List<Fertilizer> selectFertilizerList(int fert_depth, int fert_id) {
		
		return mapper.selectFertilizerList(fert_depth, fert_id);
	}

}
