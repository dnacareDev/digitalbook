package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Research")
public class Research
{
	private int research_id;					// 조사방법 번호
	private String research_code;				// 조사방법 코드
	private String research_contents;			// 조사방법 내용
	private String create_date;
	private String modify_date;
	
	private int division_id;					// 조사항목 번호
	
	private String d1;							// 조사항목(작목)
	private String d2;							// 조사항목(분류)
	private String d3;							// 조사항목(항목)
}