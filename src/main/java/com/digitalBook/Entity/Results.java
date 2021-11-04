package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Results")
public class Results {
	
	private int results_id;					//결과입력 고유번호
	private int plan_id;					//재배계획 고유번호
	private int segment_id;					//구획번호
	private int individual_id;				//개체번호
	private int individual_index;			//프로토콜 인덱스
	private String results_content;			//프로토콜 값
	private String results_file;			//변경한 사진 이름
	private String results_origin_file;		//원본 사진 이름
	
}
