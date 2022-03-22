package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Board;
import com.digitalBook.Entity.Plan;
import com.digitalBook.Entity.Schedule;
import com.digitalBook.Entity.User;

public interface HomeService
{
	List<Plan> selectDelayPlanList(int user_group, int user_id);
	
	List<Plan> selectCurrentPlanList(int user_group, int user_id);

	List<Plan> selectProgressPlanList(int user_group, int user_id);

	List<Schedule> SelectUserSchedule(int user_id);
	
	List<Board> SelectUserBoard(int user_id);

	List<Schedule> SelectAdminSchedule(User user);

	List<User> SelectUser(User user);
	
	//알림에 노출할 plan list
	List<Plan> selectNonReadPlanList(int user_id);
	
	//알림 내 plan 클릭시 읽음 처리
	int updatePlanRead(int plan_id);
	int updateAllAlarm(int user_id);
	
}