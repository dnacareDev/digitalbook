package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Method")
public class Method {
	
	private int method_id;					// 재배 고유번호
	private int division_id;				// 조사항목 고유번호
	private String method_code;				// 재배 ID
	private String method_title;			// 재배 제목
	private String method_contents;			// 재배 설명
	private String method_input;			// 입력 단위 = eaches_type
	private int eaches_id;					// 단위 고유번호
	private String method_comment;			// 재배 Comment
	private int method_status;				// 재배 상태 0(승인요청), 1(수정 승인요청), 2(승인완료)
	private String create_date;				// 등록일
	private String modify_date;				// 수정일
	private int user_group;					// 사용자 그룹번호
	private int user_id;					// 등록한 사용자 공유번호
	private int method_share;				// 메소드 공유 여부
	
	private int last_method_id;				// insert 수행 후 반환되는 해당 pk 값
	
	//단위 사용 컬럼
	private String eaches_name;
	
	// 재배 프로토콜의 step 개수
	private int step_no;
	
	//division 사용 컬럼
	private String d1_name;					// 작목 명
	private String d2_name;					// 분류 명
	private String d3_name;					// 항목 명
	
	private int d1_id;						// 작목 pk
	private int d2_id;						// 분류 pk
	private int d3_id;						// 항목 pk
	
}
