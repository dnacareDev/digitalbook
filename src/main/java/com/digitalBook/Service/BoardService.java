package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Board;
import com.digitalBook.Entity.Reagent;
import com.digitalBook.Entity.User;

public interface BoardService {
	// 전달사항 등록
	int insertBoard(Board board);
		
	// 전달사항 수정
	int updateBoard(Board board);
		
	// 전달사항 삭제
	int deleteBoard(int board_id);
		
	// 전달사항 조회
	List<Board> selectBoard(int user_id, int user_group);
		
	// 전달사항 공유인원 조회
	List<User> selectShareBoard( int user_group);

	// 전달사항 count
	int searchBoardCount(String search_type, String keyword, int user_group);

	// 전달 사항 검색
	List<Board> searchBoard(String search_type, String keyword, int offset, int limit, int user_group);

	Board selectBoardId(int board_id);
}
