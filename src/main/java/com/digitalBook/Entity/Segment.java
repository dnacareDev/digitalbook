package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Segment")
public class Segment {
	
	private int segment_id;					//구획 고유번호
	private int plan_id;					//재배계획 고유번호
	private String segment_code;			//구획 ID
	private int segment_index;				//구획 번호
	private String segment_type;			//구획 처리종류
	private int segment_repeat;				//반복순서
	private int segment_area;				//구획 면적
	private int segment_aspect;				//구획 방향
	private int segment_horizon;			//구획 가로
	private int segment_vertical;			//구획 세로
	private int individual_num;				//개체수
	private String segment_grid_code;		//그리드 구획 ID
	private int segment_order;				//그리드 구획 번호
	private int segment_zindex;				//그리드 레이어 순번
	
}
