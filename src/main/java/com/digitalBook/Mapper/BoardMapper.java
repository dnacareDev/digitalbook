package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Board;
import com.digitalBook.Entity.Reagent;
import com.digitalBook.Entity.User;

@Mapper
public interface BoardMapper {
	
	// 전달사항 등록
	int insertBoard(Board board);
	
	// 전달사항 수정
	int updateBoard(Board board);
	
	// 전달사항 삭제
	int deleteBoard(int board_id);
	
	// 전달사항 조회
	List<Board> selectBoard(@Param("user_id") int user_id, @Param("user_group") int user_group);
	
	// 전달사항 공유인원 조회
	List<User> selectShareBoard( int user_group);

	// 전달사항 count
	int searchBoardCount(@Param("search_type")String search_type, @Param("keyword")String keyword, @Param("user_group")int user_group);

	// 전달사항 검색 조회
	List<Board> searchBoard(@Param("search_type")String search_type, @Param("keyword")String keyword, @Param("offset")int offset, @Param("limit")int limit, @Param("user_group")int user_group);
	
	// 전달사항 상세 조회
	Board selectBoardId(int board_id);
}
