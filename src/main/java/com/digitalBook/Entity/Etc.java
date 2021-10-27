package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Etc")
public class Etc {
	
	private int etc_id;						//기타요인 고유번호
	private String etc_type;				//기타요인 유형
	private String etc_value;				//기타요인 값
	private String etc_comment;				//기타요인 comment;
	
	private int last_etc_id;				//insert 시 생성된 pk값
	
}
