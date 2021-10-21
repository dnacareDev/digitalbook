package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Step")
public class Step {
	
	private int step_id;					// 실험, 재배 단계 고유번호
	private int method_id;					// 재배 고유번호
	private int research_id;				// 조사 고유번호(재배)
	private int step_index;					// 실험, 재배 단계
	private String step_comment;			// 실험, 재배 단계 설명
	private int step_depth;					// 실험, 재배 단계 깊이
	private int step_parents;				// 부모 실험, 재배 단계 index
	
	
	//조사 방법 사용 컬럼
	private String research_contents;		//조사방법 명
	
}
