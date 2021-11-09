package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("SegmentInfo")
public class SegmentInfo {
	
	private int info_id;					//구획정보 고유번호
	private int segment_id;					//구획 고유번호
	private int plan_id;					//재배계획 고유번호
	private int segment_index;				//구획번호
	private String info_status;				//구획정보 상태
	private int info_index;					//구획정보 번호
	
}
