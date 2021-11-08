package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Plan;
import com.digitalBook.Mapper.HomeMapper;
import com.digitalBook.Service.HomeService;

@Service
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	private HomeMapper mapper;
	
	//지난일정
	@Override
	public List<Plan> selectDelayPlanList(int user_group, int user_id) {
		
		return mapper.selectDelayPlanList(user_group, user_id);
	}

	//진행 일정
	@Override
	public List<Plan> selectCurrentPlanList(int user_group, int user_id) {
		
		return mapper.selectCurrentPlanList(user_group, user_id);
	}

	// 진행 상황
	@Override
	public List<Plan> selectProgressPlanList(int user_group, int user_id) {
		return mapper.selectCurrentPlanList(user_group, user_id);
	}

}
