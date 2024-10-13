package com.scm.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactRepo extends JpaRepository<Contact, String>{

    Contact findByEmail(String email);

    Contact findByPhoneNumber(String phoneNumber);
	
	List<Contact> findByUser(User user);
	
}

