package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("ResultPlan")
public class ResultPlan {
	
	private int rp_id;					//결과입력 고유번호
	private int plan_id;	
	private int rp_num;					//개채수
	private int rp_segment_count;					//개채수
	private String rp_content;			//프로토콜 값
	private String rp_segment;			//새그먼트 값
	
	private String create_date;			//프로토콜 값
	private String modfiy_date;			//프로토콜 값
	
}
