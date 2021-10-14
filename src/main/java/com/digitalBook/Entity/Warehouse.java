package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Warehouse")
public class Warehouse {
	
	private int ware_id;					//저장장소 고유번호
	private String warehouse;				//저장 장소명
	private String ware_condition;			//저장 조건

}
