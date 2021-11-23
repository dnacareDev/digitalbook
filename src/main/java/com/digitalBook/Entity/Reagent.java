package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Reagent")
public class Reagent {
	
	private int reagent_id;					//시약 고유번호
	private String reagent_code;			//시약 ID
	private String reagent_name;			//시약 품명
	private String reagent_standard;		//시약 규격
	private String reagent_formula;			//시약 화학식
	private String reagent_nick_k;			//시약 별칭(국문)
	private String reagent_nick_e;			//시약 별칭(영문)
	private String reagent_manufacturer;	//시약 제조사
	private int reagent_quantity;			//시약 수량
	private int eaches_id;					//단위 고유번호
	private String reagent_purpose;			//시약 용도
	private String reagent_type;			//시약 유형
	private int reagent_status;				//시약 상태
	private String create_date;				//등록일
	private String modify_date;				//수정일
	private int user_id;					//등록자
	private int user_group;					//사용자 그룹번호
	
	//user table 사용 컬럼
	private String user_name_k;				//등록자
	
	//단위 사용 컬럼
	private String eaches_name;				//단위 명
	
}
