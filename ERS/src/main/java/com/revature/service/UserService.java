package com.revature.service;

import com.revature.data.UserDOA;
import com.revature.model.User;

public class UserService {
	
	public static User logIn(String username, String password) {
		User user = UserDOA.getByUsername(username);
		if(user == null) {
			return null; //no user by username
		}
		else {
			if(user.getPassword().equals(password)) {
				return user; //success!
			}
			else {
				return null; //password does not match 
			}
		}
	}

}
