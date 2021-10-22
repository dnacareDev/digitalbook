package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Material")
public class Material {
	
	private int material_id;					// 농자재 고유번호
	private int user_id;						// 사용자 고유번호
	private String material_code;				// 농자재 ID
	private String material_name;				// 농자재명
	private int material_status;				// 농자재 상태 0(미사용) 1(사용)
	private int user_group;						// 사용자 그룹번호
	private String create_date;					// 농자재 등록일
	private String modify_date;					// 농자재 수정일
	
	//사용자 user 컬럼
	private String user_name_k;					// 등록자
	
}
