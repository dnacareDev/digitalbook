package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Schedule;
import com.digitalBook.Entity.User;

public interface ScheduleService
{
	List<Schedule> SelectUserSchedule(int user_id);

	List<Schedule> SelectAdminSchedule(User user);
	
	Schedule SelectScheduleDetail(int sch_id);
}