package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Division")
public class Division
{
	private int division_id;				// 조사항목 번호
	private String division;				// 조사항목
	private int division_depth;				// 조사항목 깊이
	private int division_parents;			// 부모 조사항목
	
	private int genetic_id;					// 품종/유전 정보 번호
}