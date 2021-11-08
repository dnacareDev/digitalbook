package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Plan;
import com.digitalBook.Entity.Schedule;

public interface HomeService
{
	List<Plan> selectDelayPlanList(int user_group, int user_id);
	
	List<Plan> selectCurrentPlanList(int user_group, int user_id);

	List<Plan> selectProgressPlanList(int user_group, int user_id);

	List<Schedule> SelectUserSchedule(int user_id);
}