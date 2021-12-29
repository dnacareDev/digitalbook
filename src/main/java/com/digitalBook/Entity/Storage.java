package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Storage")
public class Storage
{
	private int storage_id;							// 장소 고유번호
	private String storage_code;					// 장소 코드
	private String storage_name;					// 장소 위치명
	private int storage_type;						// 장소 유형(0: 작물연구동, 1: 가공이용연구동, 2: 연천시험지, 3: 외부포장)
	private String storage_division;				// 장소 구회구분
	private String storage_size;					// 장소 면적
	private String storage_location;				// 장소 좌표
	private String storage_address;					// 좌표 선택시 주소
	private String create_date;
	private String modify_date;
	private int storage_status;						//장소 상태 0(미사용) 1(사용중)
	private int user_id;							//등록자 고유번호 (0 : 관리자)
	private String storage_unit;
	private int depart_id;							// 장소 이용과
	private String department;
}