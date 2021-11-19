package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Board;
import com.digitalBook.Entity.Plan;
import com.digitalBook.Entity.Schedule;
import com.digitalBook.Entity.User;

@Mapper
public interface HomeMapper
{
	//지난 일정
	List<Plan> selectDelayPlanList(@Param("user_group") int user_group, @Param("user_id") int user_id);
	
	//진행 일정
	List<Plan> selectCurrentPlanList(@Param("user_group") int user_group, @Param("user_id") int user_id);

	// 전체 일정(사용자)
	List<Schedule> SelectUserSchedule(int user_id);
	
	List<Board> SelectUserBoard(int user_id);

	// 전체 일정(관리자)
	List<Schedule> SelectAdminSchedule(User user);

	List<User> SelectUser(User user);
	
	//알림에 노출할 plan list
	List<Plan> selectNonReadPlanList(int user_id);
	
	//알림 내 plan 클릭시 읽음 처리
	int updatePlanRead(int plan_id);
	
}