package com.digitalBook.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("User")
public class User implements UserDetails
{
	private int user_id;					//사용자 고유번호
	private String user_username;			//사용자 아이디
	private String user_password;			//사용자 비밀번호
	private String user_name_k;				//사용자 이름(국문)
	private String user_name_e;				//사용자 이름(영문)
	private String user_post;				//사용자 우편번호
	private String user_address;			//사용자 주소
	private String user_address_detail;		//사용자 상세 주소
	private String user_tel;				//사용자 전화번호
	private String user_phone;				//사용자 휴대번호
	private String user_pax;				//사용자 팩스번호
	private String user_authority;			//사용자 권한
	private int user_type;					//사용자 유형
	private int user_status;				//사용자 상태
	private int depart_id;					//소속실 고유번호
	private String user_position;			//사용자 직종
	private String user_level;				//사용자 직급
	private String login_date;				//최종 접속일
	private String create_date;				//등록일
	private String modify_date;				//수정일
	private String user_scientist;			//과학기술인번호
	private int user_group;					//사용자 그룹번호
	
	//소속실(department) 사용 컬럼
	private String d4_department_name;		//소속실명
	private int d4_id;						//소속실 depart_id
	private String d3_department_name;		//과 명
	private int d3_id;						//과 depart_id
	private int d2_id;						//부 depart_id
	private int d1_id;						//원 depart_id
	
	private int delay_count;
	private int now_count;
	private int future_count;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user_authority));
		return authorities;
	}
	
	@Override
	public String getPassword()
	{
		return this.user_password;
	}

	@Override
	public String getUsername()
	{
		return this.getUser_username();
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return false;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return false;
	}

	@Override
	public boolean isEnabled()
	{
		return false;
	}
}