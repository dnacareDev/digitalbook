package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Fertilizer")
public class Fertilizer {
	
	private int fert_id;					//비료 고유번호
	private String fertilizer;				//비료명
	private Double fert_amount;				//비료 성분량
	private int fert_depth;					//비료 깊이
	private int fert_parent;				//부모 비료
	
}
