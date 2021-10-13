package com.digitalBook.Entity;

import lombok.Setter;
import lombok.ToString;

import org.apache.ibatis.type.Alias;

import lombok.Getter;

@Getter
@Setter
@ToString
@Alias("Machine")
public class Machine
{
	private int machine_id;
	private String machine_code;				// 장비 코드
	private String machine_name;				// 장비명
	private String machine_model;				// 장비 모델명
	private int machine_price;					// 장비 금액
	private int machine_range;					// 장비 활용 범위(0: 공동 활용, 1: 단독활용, 9: 비선택)
	private int machine_public;					// 장비 공동 활용 범위(0: 기관 내부, 1: 기관 외부, 9: 비선택)
	private String machine_purpose;				// 장비 용도(계측, 시험, 분석, 기타)
	private String machine_place;				// 장비 설치 장소
	private String machine_regist;				// 장비 등록 번호
	private String machine_manage;				// 장비 고정 자산 관리 번호
	private int machine_status;					// 장비 상태(0: 미사용, 1: 사용)
	private String machine_date;				// 취득 일자
	private String create_date;
	private String modify_date;
	
	private int user_id;
}