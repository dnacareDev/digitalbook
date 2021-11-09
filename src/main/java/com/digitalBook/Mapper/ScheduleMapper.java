package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalBook.Entity.Schedule;

@Mapper
public interface ScheduleMapper
{
	// 전체 일정(사용자)
	List<Schedule> SelectUserSchedule(int user_id);

	Schedule SelectScheduleDetail(int sch_id);
}