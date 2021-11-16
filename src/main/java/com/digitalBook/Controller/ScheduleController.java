package com.digitalBook.Controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Board;
import com.digitalBook.Entity.Schedule;
import com.digitalBook.Entity.User;
import com.digitalBook.Service.ScheduleService;

@Controller
@RequestMapping("/schedule")
public class ScheduleController
{
	@Autowired
	private ScheduleService service;
	
	// 월간 일정 페이지
	@RequestMapping("month")
	public ModelAndView Month(ModelAndView mv, Authentication auth)
	{
		User prin = (User)auth.getPrincipal();
		
		if(prin.getUser_type() == 0)
		{
			mv.setViewName("schedule/admin_month");
		}
		else
		{
			mv.setViewName("schedule/user_month");
		}
		
		return mv;
	}
	
	// 사용자 일정
	@ResponseBody
	@RequestMapping("selectUserSchedule")
	public Map<String, Object> SelectUserSchedule(Authentication auth)
	{
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		User prin = (User)auth.getPrincipal();
		
		List<Schedule> schedule = service.SelectUserSchedule(prin.getUser_id());
		List<Board> board = service.SelectUserBoard(prin.getUser_id());
		
		result.put("schedule", schedule);
		result.put("board", board);
		
		return result;
	}
	
	// 관리자 일정
	@ResponseBody
	@RequestMapping("selectAdminSchedule")
	public Map<String, Object> SelectAdminSchedule(Authentication auth)
	{
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		User prin = (User)auth.getPrincipal();
		
		List<Schedule> schedule = service.SelectAdminSchedule(prin);
		List<Board> board = service.SelectUserBoard(prin.getUser_id());
		
		result.put("schedule", schedule);
		result.put("board", board);
		
		return result;
	}
	
	// 일정 상세 조회
	@ResponseBody
	@RequestMapping("selectScheduleDetail")
	public Schedule SelectScheduleDetail(@RequestParam("sch_id") int sch_id)
	{
		Schedule result = service.SelectScheduleDetail(sch_id);
		
		return result;
	}
	
	// 전달사항 목록
	@RequestMapping("/board")
	public ModelAndView Board(ModelAndView mv)
	{
		mv.setViewName("board/board_list");
		
		return mv;
	}
	
	// 전달사항 검색
	@ResponseBody
	@RequestMapping("/searchBoard")
	public Map<String,Object> SearchBoard(Authentication auth, @RequestParam(name = "search_type", required = false) String search_type, @RequestParam(name = "keyword", required = false) String keyword, @RequestParam("page_num") int page_num, @RequestParam("limit") int limit)
	{
		Map<String, Object> result = new LinkedHashMap<>();
		
		User prin = (User) auth.getPrincipal();
		
		int count = service.SearchBoardCount(search_type, keyword, prin.getUser_group());
		int offset = (page_num - 1) * limit;
		int start_page = ((page_num - 1) / 10) * 10 + 1;
		int end_page = (count + limit - 1) / limit;
		
		List<Board> board = service.SearchBoard(search_type, keyword, offset, limit, prin.getUser_group());
		
		result.put("board", board);
		result.put("page_num", page_num);
		result.put("start_page", start_page);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
	
	// 전달사항 등록 페이지
	@RequestMapping(value = "/boardInsert")
	public ModelAndView BoardInsert(ModelAndView mv,Authentication auth)
	{
		User prin = (User) auth.getPrincipal();

		List<User> user = service.SelectShareBoard(prin.getUser_group());
		
		mv.addObject("user", user);
		
		mv.setViewName("board/board_insert");
		
		return mv;
	}
	
	// 전달사항 수정 페이지
	@ResponseBody
	@RequestMapping("/boardModify")
	public ModelAndView BoardModify(ModelAndView mv, Authentication auth, @PathParam("board_id") int board_id)
	{
		User prin = (User) auth.getPrincipal();
		
		Board board = service.SelectBoardDetail(board_id);
		
		List<User> user = service.SelectShareBoard(prin.getUser_group());
		
		String[] share_list = board.getBoard_share().split(",");
		
		for(int i = 0 ; i < user.size(); i++)
		{
			for(int j = 0; j < share_list.length; j++)
			{				
				if(user.get(i).getUser_id() == Integer.parseInt(share_list[j]))
				{
					user.get(i).setD1_id(1);
				}
			}
		}
		
		mv.addObject("board", board);
		mv.addObject("user", user);
		mv.addObject("share_list", share_list);
		
		mv.setViewName("board/board_modify");
		
		return mv;
	}

	// 전달사항 등록
	@ResponseBody
	@RequestMapping("/insertBoard")
	public int InsertBoard(Authentication auth, @ModelAttribute Board board)
	{
		User prin = (User) auth.getPrincipal();
		
		board.setUser_id(prin.getUser_id());
		board.setUser_group(prin.getUser_group());
		
		int result = service.InsertBoard(board); 
		
		return result;
	}
	
	// 전달사항 수정
	@ResponseBody
	@RequestMapping("/updateBoard")
	public int UpdateBoard(@ModelAttribute Board board)
	{
		int result = service.UpdateBoard(board);
		
		return result;
	}

	// 전달사항 삭제
	@ResponseBody
	@RequestMapping("/deleteBoard")
	public int DeleteBoard(@RequestParam("board_id") int board_id)
	{
		int result = service.DeleteBoard(board_id);
		
		return result;
	}
	
	// 전달사항 상세 조회
	@ResponseBody
	@RequestMapping("selectBoardDetail")
	public Board SelectBoardDetail(@RequestParam("board_id") int board_id)
	{
		Board result = service.SelectBoardDetail(board_id);
		
		return result;
	}
}