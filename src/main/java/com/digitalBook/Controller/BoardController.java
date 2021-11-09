package com.digitalBook.Controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler;

import com.digitalBook.Entity.Board;
import com.digitalBook.Entity.Reagent;
import com.digitalBook.Entity.User;
import com.digitalBook.Service.BoardService;

@Controller
@RequestMapping(value= "/")
public class BoardController {

	@Autowired
	private BoardService service;
	
	// 전달사항 등록 페이지
	@RequestMapping(value = "board/insert")
	public ModelAndView boardInsert(ModelAndView mv,Authentication auth) {
		User prin = (User) auth.getPrincipal();
		prin.getUser_id();
		prin.getUser_group();
		List<User>userGroup = service.selectShareBoard(prin.getUser_group());
		mv.addObject("userGroup", userGroup);
		mv.setViewName("board/board_insert");
		// 공유자 조회
		
		return mv;
	}
	
	
	@RequestMapping(value="baord/insertBoard")
	@ResponseBody
	public int insertBoard(Board board, Authentication auth) {
		System.out.println(board.toString());
		User prin = (User) auth.getPrincipal();
		board.setUser_id(prin.getUser_id());
		board.setUser_group(prin.getUser_group());
		
		return service.insertBoard(board);
	}
	
	@RequestMapping(value="board/modify")
	@ResponseBody
	public ModelAndView boardModify(ModelAndView mv, Authentication auth, @PathParam(value="board_id")int board_id) {
		Board boardList = service.selectBoardId(board_id);
		User prin = (User) auth.getPrincipal();
		List<User>userGroup = service.selectShareBoard(prin.getUser_group());
		String[] share_list = boardList.getBoard_share().split(",");
		
		for(int i =0 ; i< userGroup.size(); i++)
		{
			for(int j=0; j<share_list.length; j++)
			{				
				if(userGroup.get(i).getUser_id() == Integer.parseInt(share_list[j]))
				{
					userGroup.get(i).setD1_id(1);
				}
			}
		}
		
		mv.addObject("userGroup", userGroup);
		mv.addObject("board",boardList);
		mv.addObject("share_list",share_list);
		
		mv.setViewName("board/board_modify");
		
		return mv;
	}
	
	@RequestMapping(value="board/update")
	@ResponseBody
	public Map<String,Object> updateBoard(Board board,Authentication auth, @PathParam(value="board_id")int board_id) {
		Map<String, Object> result = new LinkedHashMap<>();
		Board boardList = service.selectBoardId(board_id);
		service.updateBoard(board);
		result.put("board", board);
		return result;
	}

	@RequestMapping(value="board/delete")
	public int deleteBoard(int board_id) {
		return service.deleteBoard(board_id);
	}

	@RequestMapping(value="board")
	public ModelAndView selectBoard(ModelAndView mv, Authentication auth) {
		User prin = (User) auth.getPrincipal();
		List<Board> board_list = service.selectBoard(prin.getUser_id(), prin.getUser_group());
		mv.addObject("board_list",board_list);
		mv.setViewName("/board/board_list");
		return mv;
	}
	
	@RequestMapping(value="/board/searchBoard")
	@ResponseBody
	public Map<String,Object> searchBoard(Authentication auth,
											@RequestParam(name = "search_type", required = false) String search_type,
											@RequestParam(name = "keyword", required = false) String keyword,
											@RequestParam("page_num") int page_num,
											@RequestParam("limit") int limit){
		Map<String, Object> result = new LinkedHashMap<>();
		User prin = (User) auth.getPrincipal();
		int count = service.searchBoardCount(search_type, keyword, prin.getUser_group());
		
		int offset = (page_num - 1) * limit;
		int end_page = (count + limit - 1) / limit;
		
		List<Board> board = service.searchBoard(search_type, keyword, offset, limit, prin.getUser_group());
		result.put("board", board);
		result.put("page_num", page_num);
		result.put("end_page", end_page);
		result.put("offset", offset);
		return result;
	}
	
}
