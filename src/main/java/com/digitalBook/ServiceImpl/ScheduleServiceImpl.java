package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Schedule;
import com.digitalBook.Mapper.ScheduleMapper;
import com.digitalBook.Service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService
{
	@Autowired
	private ScheduleMapper mapper;
	
	// 전체 일정(사용자)
	@Override
	public List<Schedule> SelectUserSchedule(int user_id)
	{
		return mapper.SelectUserSchedule(user_id);
	}

	@Override
	public Schedule SelectScheduleDetail(int sch_id)
	{
		return mapper.SelectScheduleDetail(sch_id);
	}
}