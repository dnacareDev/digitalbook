package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Report")
public class Report {
	
	private int report_id;						// 과제 고유번호
	private String user_name;					// 책임자
	private String report_code;					// 과제 ID(협약번호)
	private String report_number;				// 과제번호
	private String report_title;				// 과제명
	private int report_year;					// 과제 년도
	private String report_contents;				// 과제 설명
	private String report_file;					// 과제 ATIS 파일
	private String report_origin_file;			// 과제 원본 파일
	private String create_date;					// 등록일
	private String modify_date;					// 수정일
	private int user_group;						// 사용자 그룹
	private int user_id;						// 등록인 고유번호
	
	private int Last_report_id;					// 과제 등록시 해당 과제 고유번호 반환 받는 변수
	
}
