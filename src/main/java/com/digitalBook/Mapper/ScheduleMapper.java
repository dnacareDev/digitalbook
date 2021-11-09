package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Board;
import com.digitalBook.Entity.Schedule;
import com.digitalBook.Entity.User;

@Mapper
public interface ScheduleMapper
{
	// 전체 일정(사용자)
	List<Schedule> SelectUserSchedule(int user_id);
	
	List<Schedule> SelectAdminSchedule(User user);

	Schedule SelectScheduleDetail(int sch_id);
	
	// 전달사항 갯수 조회
	int SearchBoardCount(@Param("search_type") String search_type, @Param("keyword") String keyword, @Param("user_group") int user_group);
	
	// 전달사항 검색
	List<Board> SearchBoard(@Param("search_type") String search_type, @Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit, @Param("user_group") int user_group);
	
	// 전달사항 공유인원 조회
	List<User> SelectShareBoard(int user_group);
	
	// 전달사항 조회
	List<Board> SelectBoard(@Param("user_id") int user_id, @Param("user_group") int user_group);
	
	// 전달사항 상세 조회
	Board SelectBoardId(int board_id);
	
	// 전달사항 등록
	int InsertBoard(Board board);
	
	// 전달사항 수정
	int UpdateBoard(Board board);
	
	// 전달사항 삭제
	int DeleteBoard(int board_id);
}