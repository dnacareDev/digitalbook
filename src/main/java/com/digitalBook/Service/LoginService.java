package com.digitalBook.Service;

import com.digitalBook.Entity.User;

public interface LoginService
{
	User loadUserByUsername(String user_username);
}