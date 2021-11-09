package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Schedule;

public interface ScheduleService
{
	List<Schedule> SelectUserSchedule(int user_id);

	Schedule SelectScheduleDetail(int sch_id);
}