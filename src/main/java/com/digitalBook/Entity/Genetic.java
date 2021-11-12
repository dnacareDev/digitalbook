package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Genetic")
public class Genetic
{
	private int genetic_id;					// 품종/유전 정보 번호
	private String genetic;					// 품종/유전 정보
	private int genetic_type;				// 품종/유전 정보 유형(0: 품종, 1: 유전자원, 2: 기타)
	
	private int division_id;
	
	private int last_genetic_id;			//등록 후 생성된 pk값
}