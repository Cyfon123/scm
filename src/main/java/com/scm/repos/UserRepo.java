package com.scm.repos;


import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.entities.User;

public interface UserRepo extends JpaRepository<User, String>{
	
	User findByEmail(String email);
	
	User findByPhoneNumber(String phoneNumber);
	
	User findByEmailAndPassword(String email, String password);

	User findByPhoneNumberAndPassword(String phoneNumber, String password);
}
