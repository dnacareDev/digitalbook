package com.digitalBook.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.User;
import com.digitalBook.Mapper.LoginMapper;
import com.digitalBook.Service.LoginService;

@Service
public class LoginServiceImpl implements LoginService
{
	@Autowired
	private LoginMapper mapper;
	
	// 로그인
	@Override
	public User loadUserByUsername(String user_username) throws UsernameNotFoundException
	{
		User user = mapper.LoginUserInfo(user_username);
		
		if(user == null)
		{
			return null;
		}
		else
		{
			return user;
		}
	}
}