package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Manure")
public class Manure {
	
	private int manure_id;					//시비량 고유번호
	private int fert_id;					//비료 고유번호
	private int manure_area;				//시비량 면적
	private String manure_ingredient;		//시비량 성분요소
	private String manure_type;				//시비량 종류
	private Double manure_amount;			//시비량(kg/10a)
	private Double manure_percent;			//성분량(%)
	private Double manure_result;			//값(kg)
	private int manure_level;				//시비량 수준
	private int plan_id;					//재배 메소드 고유번호
	private int factor_index;				//요인 순서
	
	private int last_manure_id;				//insert 시 생성된 pk값
	
}
