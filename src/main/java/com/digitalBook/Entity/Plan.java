package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Plan")
public class Plan
{
	private int plan_id;					// 재배 계획 고유번호
	private int storage_id;					// 장소 고유번호
	private String plan_code;				// 재배 계획 ID
	private String report_code;				// 과제 코드
	private int grow_type;					// 시험구배치 유형 (0: 완전 임의배치법 1:난괴법 2:분할구 배치법 3:세세구 배치법
	private int plan_status;				// 계획 상태
	private int plan_repeat;				// 반복 횟수
	private int plan_segment;				// 구획 열 갯수
	private String plan_method;				// 재배 조사항목(json 배열 형식)
	private int user_group;					// 사용자 그룹번호
	private String create_date;				// 등록일
	private String modify_date;				// 수정일
	private int plan_step;					// 재배계획 스텝(0: 수정가능, 1: 담당자만 수정 가능)
	private int last_plan_id;				// insert 시 등록된 pk 값
	private int plan_read;					// 읽음 여부

	//과제 사용 컬럼
	private String report_title;			// 과제명
	private String report_user_name;		// 과제 책임자명
	private int report_year;				// 과제년도
	
	//실험장소 사용 컬럼
	private String storage_name;			// 포장명
	private String storage_location;		// 장소 위치

	//재배 프로토콜 메소드명
	private String method_title;			// 메소드명
	
	//스케줄 사용 컬럼
	private String start_date;
	private String end_date;
	private String user_name_k;				// 담당자 명
	
	// 연구 노트 결과입력
	 private int rp_id;
	 private String rp_content;
	 private String rp_segment;
	 private int rp_num;
	 private int rp_segment_count;
	 private String rp_create_date;  // result_plan 생성날짜
	 
	 private String weather;
	 private String soil;
}