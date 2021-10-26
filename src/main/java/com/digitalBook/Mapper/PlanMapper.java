package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Fertilizer;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;

@Mapper
public interface PlanMapper {
	
	//종자(시료) 등록된 과제 list
	List<Report> selectReportList();
	
	//시험재료(seed) 조회 list
	List<Seed> selectSeedList(@Param("user_group") int user_group, @Param("report_id") int report_id);
	
	//비료 조회 list
	List<Fertilizer> selectFertilizerList(@Param("fert_depth") int fert_depth, @Param("fert_id") int fert_id);

}
