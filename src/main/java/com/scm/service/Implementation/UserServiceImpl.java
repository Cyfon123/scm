package com.scm.service.Implementation;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.exceptions.ResourceNotFoundException;
import com.scm.repos.UserRepo;
import com.scm.services.UserService;

@Service
public class UserServiceImpl  implements UserService{
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public User create(User user) {
		String userId = UUID.randomUUID().toString();
		user.setUserId(userId);
		return userRepo.save(user);
	}

	@Override
	public User getUser(String userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found :- " +userId));
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public User getUserByPhoneNumber(String phoneNumber) {
		return userRepo.findByPhoneNumber(phoneNumber);
	}

	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		return userRepo.findByEmailAndPassword(email, password);
	}

	@Override
	public User getUserByPhoneNumberAndPassword(String phoneNumber, String password) {
		return userRepo.findByPhoneNumberAndPassword(phoneNumber, password);
	}

	@Override
	public void deleteUser(String userId) {
		userRepo.deleteById(userId);
	}

}
