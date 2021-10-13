package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Eaches")
public class Eaches {
	
	private int eaches_id;					//단위 고유번호
	private String eaches_name;				//단위명
	private String eaches_type;				//단위 유형
	
}
