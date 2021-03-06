package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Seed")
public class Seed
{
	private int seed_id;					// 시료 고유번호
	private int seed_manager;				// 담당자 고유번호
	private String seed_code;				// 시료 ID
	private String seed_area;				// 시료 생산지역
	private int seed_year;					// 시료 생산년도
	private String seed_method;				// 시료 생산방법
	private int seed_amount;				// 시료 수량
	private int eaches_id;					// 단위 고유번호
	private String seed_comment;			// 시료 comment
	private int genetic_id;					// 정보 고유번호
	private String report_code;				// 과제 ID
	private String seed_report;				// 과제 설명
	private int ware_id;					// 저장장소 고유번호
	private String seed_sender;				// 발송인
	private String send_date;				// 발송일자
	private int seed_receiver;				// 수취인
	private String receive_date;			// 수취일자
	private int seed_share;					// 수확후 이용과 공유
	private String create_date;				// 등록일
	private String modify_date;				// 수정일
	private int user_group;					// 사용자 그룹번호
	private int seed_status;				// 시료 상태 0(승인요청), 1(수정요청), 2(승인)
	
	private int seed_count;					// 과제당 시료수
	
	private int last_seed_id;				// 시료 등록시 해당 시료 고유번호 반환 받는 변수
	
	// 과제(report) 컬럼
	private int report_id;					//과제 고유번호
	private String report_title;			// 과제명
	private String report_user_name;		// 과제 책임자명
	
	// 작목 컬럼(division)
	private String division;				// 작목명(조사항목)
	
	// 품종,유전 정보 컬럼(genetic)
	private String genetic;					// 정보명
	private int genetic_type;				// 품종, 유전 타입
	
	// 보관장소 컬럼(warehouse)
	private String warehouse;				// 저장 장소명
	private String ware_condition;			// 저장 조건
	
	// 담당자 컬럼
	private String manager_name;			// 담당자 명
	private String receive_name;
	
	// 단위 컬럼
	private String eaches_name;				//단위명
	
}