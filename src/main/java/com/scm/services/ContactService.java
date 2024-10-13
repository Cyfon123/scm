package com.scm.services;

import java.util.List;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {

    // Create a new contact
    Contact create(Contact contact);

    // Get a contact by ID
    Contact getContact(String contactId);

    // Delete a contact by ID
    void deleteContact(String contactId);

    // Update a contact
    Contact updateContact(Contact contact);

    // Find contact by user's email (within the email collection)
    Contact getContactByEmail(String email);

    // Find contact by user's phone number (within the phone number collection)
    Contact getContactByPhoneNumber(String phoneNimber);

    // Find contacts associated with a user
    List<Contact> getContactsByUser(User user);
}
