package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Board;
import com.digitalBook.Entity.Schedule;
import com.digitalBook.Entity.User;
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
	public List<Schedule> SelectAdminSchedule(User user)
	{
		return mapper.SelectAdminSchedule(user);
	}

	@Override
	public Schedule SelectScheduleDetail(int sch_id)
	{
		return mapper.SelectScheduleDetail(sch_id);
	}

	// 전달사항 갯수 조회
	@Override
	public int SearchBoardCount(String search_type, String keyword, int user_group)
	{
		return mapper.SearchBoardCount(search_type, keyword, user_group);
	}
	
	// 전달사항 검색
	@Override
	public List<Board> SearchBoard(String search_type, String keyword, int offset, int limit, int user_group)
	{
		return mapper.SearchBoard(search_type, keyword, offset, limit, user_group);
	}
	
	// 전달사항 공유인원 조회
	@Override
	public List<User> SelectShareBoard(int user_group)
	{
		return mapper.SelectShareBoard(user_group);
	}

	// 전달사항 조회
	@Override
	public List<Board> SelectBoard(int user_id, int user_group)
	{
		return mapper.SelectBoard(user_id, user_group);
	}

	@Override
	public Board SelectBoardId(int board_id)
	{
		return mapper.SelectBoardId(board_id);
	}
	
	// 전달사항 등록
	@Override
	public int InsertBoard(Board board)
	{
		return mapper.InsertBoard(board);
	}

	// 전달사항 수정
	@Override
	public int UpdateBoard(Board board)
	{
		return mapper.UpdateBoard(board);
	}
	
	// 전달사항 삭제
	@Override
	public int DeleteBoard(int board_id)
	{
		return mapper.DeleteBoard(board_id);
	}
}