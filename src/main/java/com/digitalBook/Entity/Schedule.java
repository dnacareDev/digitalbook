package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Schedule")
public class Schedule
{
	private int sch_id;						// 재배 일정 고유번호
	private int plan_id;					// 재배계획 고유번호
	private int sch_supervisor;				// 담당자(정)
	private int sch_manager;				// 담당자(부)
	private String start_date;				// 시작일
	private String end_date;				// 종료일
	private String sch_comment;				// 일정 코멘트
	private String create_date;
	private String modify_date;
	
	// 담당자 사용 컬럼
	private String u1_supervisor;			// 담당자(정) 이름
	private String u2_manager;				// 담당자(부) 이름
	
	// 재배 프로토콜
	private int method_id;					// 재배 프로토콜 고유 번호
	private String method_title;			// 메소드명
}