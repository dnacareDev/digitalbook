package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Board;
import com.digitalBook.Entity.Reagent;
import com.digitalBook.Entity.User;
import com.digitalBook.Mapper.BoardMapper;
import com.digitalBook.Service.BoardService;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardMapper mapper;

	@Override
	public int insertBoard(Board board) {
		return mapper.insertBoard(board);
	}

	@Override
	public int updateBoard(Board board) {
		return mapper.updateBoard(board);
	}

	@Override
	public int deleteBoard(int board_id) {
		return mapper.deleteBoard(board_id);
	}

	@Override
	public List<Board> selectBoard(int user_id, int user_group) {
		return mapper.selectBoard(user_id, user_group);
	}

	@Override
	public List<User> selectShareBoard(int user_group) {
		return mapper.selectShareBoard(user_group);
	}

	@Override
	public int searchBoardCount(String search_type, String keyword, int user_group) {
		return mapper.searchBoardCount(search_type, keyword, user_group);
	}

	@Override
	public List<Board> searchBoard(String search_type, String keyword, int offset, int limit, int user_group) {
		return mapper.searchBoard(search_type, keyword, offset, limit, user_group);
	}

	@Override
	public Board selectBoardId(int board_id) {
		return mapper.selectBoardId(board_id);
	}
	
	
}
