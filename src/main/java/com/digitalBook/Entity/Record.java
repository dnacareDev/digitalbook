package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Record")
public class Record {
	
	private int record_id;					//변경 이력 고유번호
	private int record_status;				//변경 이력 상태 0(승인요청), 1(수정요청), 2(승인)
	private int record_type;				//변경 이력 원 데이터의 고유번호
	private String record_date;				//변경 이력 날짜
	
}
