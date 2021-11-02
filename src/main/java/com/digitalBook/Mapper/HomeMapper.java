package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Plan;

@Mapper
public interface HomeMapper {
	
	//지난 일정
	List<Plan> selectDelayPlanList(@Param("user_group") int user_group, @Param("user_id") int user_id);
	
	//진행 일정
	List<Plan> selectCurrentPlanList(@Param("user_group") int user_group, @Param("user_id") int user_id);
	
}
