package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Board;
import com.digitalBook.Entity.Schedule;
import com.digitalBook.Entity.User;

public interface ScheduleService
{
	List<Schedule> SelectUserSchedule(int user_id);
	
	List<Board> SelectUserBoard(int user_id);

	List<Schedule> SelectAdminSchedule(User user);
	
	Schedule SelectScheduleDetail(int sch_id);
	
	// 전달사항 갯수 조회
	int SearchBoardCount(String search_type, String keyword, int user_group);
	
	// 전달 사항 검색
	List<Board> SearchBoard(String search_type, String keyword, int offset, int limit, int user_group);
	
	// 전달사항 공유인원 조회
	List<User> SelectShareBoard( int user_group);
		
	// 전달사항 조회
	List<Board> SelectBoard(int user_id, int user_group);
	
	Board SelectBoardDetail(int board_id);
	
	// 전달사항 등록
	int InsertBoard(Board board);
	
	// 전달사항 수정
	int UpdateBoard(Board board);
	
	// 전달사항 삭제
	int DeleteBoard(int board_id);
}