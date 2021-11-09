package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@lombok.ToString
@Alias("board")
public class Board {
	private int board_id;
	private String board_title;
	private String board_start;
	private String board_end;
	private String board_content;
	private String board_share;
	private int user_id;
	private int user_group;
}
