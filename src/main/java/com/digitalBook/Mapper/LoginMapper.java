package com.digitalBook.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.digitalBook.Entity.User;

@Mapper
public interface LoginMapper
{
	User LoginUserInfo(String user_username);
}