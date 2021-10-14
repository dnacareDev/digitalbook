package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("User")
public class User {
	
	private int user_id;					//사용자 고유번호
	private String user_username;			//사용자 아이디
	private String user_password;			//사용자 비밀번호
	private String user_name;				//사용자 이름
	
}
