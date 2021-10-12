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
	private int genetic_type;				// 품종/유전 정보 유형
	private int genetic_depth;				// 품종/유전 정보 깊이
	private int genetic_parents;			// 부모 품종/유전 정보
}