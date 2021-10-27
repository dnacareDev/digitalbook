package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Factor")
public class Factor {
	
	private int factor_id;					//배치 요인 고유번호
	private int plan_id;					//재배 계획 고유번호
	private int factor_index;				//요인 순서
	private String factor_type;				//배치 요인 유형 재식거리 시비량 등등
	private String factor_seed;				//시험재료 고유번호
	private String factor_interval;			//재식거리
	private String factor_plants;			//재식주수
	private String factor_amount;			//파종량
	private String manure_id;				//시비량 고유번호
	private String etc_id;					//기타 요인 고유번호
	
}
