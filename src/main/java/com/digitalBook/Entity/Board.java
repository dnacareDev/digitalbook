package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@lombok.ToString
@Alias("Board")
public class Board
{
	private int board_id;
	private String board_title;
	private String board_start;
	private String board_end;
	private String board_content;
	private String board_share;
	private String create_date;
	private String modify_date;
	
	private int user_id;
	private int user_group;
	private String user_name_k;
	
	private int depart_id;
	private String department;
}