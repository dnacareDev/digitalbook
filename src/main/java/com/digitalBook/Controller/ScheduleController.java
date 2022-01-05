package com.digitalBook.Controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.digitalBook.Entity.Factor;
import com.digitalBook.Entity.Plan;
import com.digitalBook.Entity.Report;
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
	
	// 연구결과 일정
	@ResponseBody
	@RequestMapping("selectResult")
	public Map<String, Object> selectResult(Authentication auth)
	{
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		User prin = (User)auth.getPrincipal();
		List<Plan> plan = service.selectResult(prin);
		result.put("plan", plan);		
		return result;
	}
	
	// 연구결과 일정
	@ResponseBody
	@RequestMapping("selectFactor")
	public Map<String, Object> selectFactor(@RequestParam("plan_id") int plan_id )
	{
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<Factor> list = new ArrayList<Factor>();
		list = service.selectFactor(plan_id);
		result.put("list", list);		
		
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
	
	@RequestMapping("listResultSchedule")
	public ModelAndView listResultSchedule(ModelAndView mv) {

		mv.setViewName("schedule/result_schedule");
		return mv;
	}
	
	@RequestMapping("listResultReport")
	public ModelAndView listResultReport(ModelAndView mv) {
		List<Report> report = service.listResultReport();
		mv.addObject("report", report);
		mv.setViewName("schedule/listResultReport");
		return mv;
	}
	
	
	@RequestMapping("searchReport")
	@ResponseBody
	public Map<String,Object> searchReport(@RequestParam(name = "report_code", required = false) String report_code,
												@RequestParam("page_num") int page_num,
												@RequestParam("limit") int limit,
												Authentication auth
												) {
	
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		int count = service.SearchReportCount(report_code);
		
		int offset = (page_num - 1) * limit;
		int start_page = ((page_num - 1) / 10) * 10 + 1;
		int end_page = (count + limit - 1) / limit;
		HashMap<String,Object> param = new HashMap<String,Object>();
		User prin = (User)auth.getPrincipal();
		
		param.put("report_code", report_code);
		param.put("offset", offset);
		param.put("limit", limit);
		param.put("user_group", prin.getUser_group());
		
		
		List<Plan> report = service.SearchReport(param);
		
		map.put("page_num", page_num);
		map.put("start_page", start_page);
		map.put("end_page", end_page);
		map.put("offset", offset);
		map.put("report", report);
		
		return map;
	}
	
	
	@RequestMapping("searchReportForAjax")
	@ResponseBody
	public Map<String,Object> searchReportForAjax(@RequestParam(name = "rp_id", required = false) int rp_id, Authentication auth
												) {
	
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		HashMap<String,Object> param = new HashMap<String,Object>();
		User prin = (User)auth.getPrincipal();
		
		param.put("offset", 0);
		param.put("limit", 10);
		param.put("user_group", prin.getUser_group());
		param.put("rp_id", rp_id);
		List<Plan> report = service.SearchReport(param);
		
		map.put("report", report);
		
		return map;
	}
}