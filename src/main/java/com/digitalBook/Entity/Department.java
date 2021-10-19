package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Department")
public class Department {
	
	private int depart_id;					//소속실 고유번호
	private String department;				//소속실명
	private int depart_depth;				//소속실 깊이
	private int depart_parents;				//부모 소속실
	
}
