package com.scm.services;

import com.scm.entities.User;

public interface UserService {

	User create(User user);
	
	User getUser(String userId);

	User getUserByEmail(String email);
	
	User getUserByPhoneNumber(String phoneNumber);
	
	User getUserByEmailAndPassword(String email, String password);
	
	User getUserByPhoneNumberAndPassword(String phoneNumber, String password);
	
	void deleteUser(String userId);
	
}
