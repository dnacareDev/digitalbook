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
	private int report_file_id;					// 과제 파일 고유번호
	private int user_group;						// 사용자 그룹
	private String create_date;					// 등록일
	private String modify_date;					// 수정일
	private int report_status;					// 과제 사용가능 여부
	
	private int report_year;

	
	
}
