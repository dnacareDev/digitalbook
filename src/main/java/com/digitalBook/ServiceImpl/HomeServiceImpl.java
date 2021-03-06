package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Board;
import com.digitalBook.Entity.Plan;
import com.digitalBook.Entity.Schedule;
import com.digitalBook.Entity.User;
import com.digitalBook.Mapper.HomeMapper;
import com.digitalBook.Service.HomeService;

@Service
public class HomeServiceImpl implements HomeService
{
	@Autowired
	private HomeMapper mapper;
	
	@Override
	public int updateAllAlarm(int user_id) {
		return mapper.updateAllAlarm(user_id);
	}
	// 지난 일정
	@Override
	public List<Plan> selectDelayPlanList(int user_group, int user_id)
	{
		return mapper.selectDelayPlanList(user_group, user_id);
	}

	// 진행 일정
	@Override
	public List<Plan> selectCurrentPlanList(int user_group, int user_id)
	{
		return mapper.selectCurrentPlanList(user_group, user_id);
	}

	// 진행 상황
	@Override
	public List<Plan> selectProgressPlanList(int user_group, int user_id)
	{
		return mapper.selectCurrentPlanList(user_group, user_id);
	}

	// 전체 일정(사용자)
	@Override
	public List<Schedule> SelectUserSchedule(int user_id)
	{
		return mapper.SelectUserSchedule(user_id);
	}
	
	@Override
	public List<Board> SelectUserBoard(int user_id)
	{
		return mapper.SelectUserBoard(user_id);
	}

	@Override
	public List<Schedule> SelectAdminSchedule(User user)
	{
		return mapper.SelectAdminSchedule(user);
	}

	@Override
	public List<User> SelectUser(User user)
	{
		return mapper.SelectUser(user);
	}
	
	//알림에 노출할 plan list
	@Override
	public List<Plan> selectNonReadPlanList(int user_id) {
		
		return mapper.selectNonReadPlanList(user_id);
	}
	
	//알림 내 plan 클릭시 읽음 처리
	@Override
	public int updatePlanRead(int plan_id) {
		return mapper.updatePlanRead(plan_id);
	}
}