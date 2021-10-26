package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("ReportFile")
public class ReportFile
{
	private int report_file_id;
	private int report_year;
	private String report_contents;
	private String report_file;
	private String report_originFile;
	private String create_date;
	private String modify_date;
	
	private int user_id;
	private String user_name_k;
}