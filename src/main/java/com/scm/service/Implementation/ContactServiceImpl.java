package com.scm.service.Implementation;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.exceptions.ResourceNotFoundException;
import com.scm.repos.ContactRepo;
import com.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private ContactRepo contactRepo;

	@Override
	public Contact create(Contact contact) {
		String contactId = UUID.randomUUID().toString();
		contact.setContactId(contactId);
		return contactRepo.save(contact);
	}

	@Override
	public Contact getContact(String contactId) {
		return contactRepo.findById(contactId)
				.orElseThrow(() -> new ResourceNotFoundException("Contact Not Found :- " + contactId));
	}

	@Override
	public void deleteContact(String contactId) {
		contactRepo.deleteById(contactId);
	}

	@Override
	public Contact updateContact(Contact contact) {
		return contactRepo.save(contact);
	}

	@Override
	public Contact getContactByEmail(String email) {
		return contactRepo.findByEmail(email);
	}

	@Override
	public Contact getContactByPhoneNumber(String phoneNumber) {
		return contactRepo.findByPhoneNumber(phoneNumber);
	}

	@Override
	public List<Contact> getContactsByUser(User user) {
		return contactRepo.findByUser(user);
	}
}
