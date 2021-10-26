package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Fertilizer;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;

public interface PlanService {
	
	//종자(시료) 등록된 과제 list
	List<Report> selectReportList();
	
	//시험재료(seed) 조회 list
	List<Seed> selectSeedList(int user_group, int report_id);
	
	//비료 조회 list
	List<Fertilizer> selectFertilizerList(int fert_depth, int fert_id);
	
}
