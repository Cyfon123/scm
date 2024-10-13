package com.scm.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Contact {

    @Id
    private String contactId;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name is required between 3 to 50 characters")
    private String name;

   
    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is required") 
    private String email;
    
    @Size(min = 10, max = 10, message = "10 digits required") 
    @NotBlank(message = "Phonenumber is required") 
    private String phoneNumber;
    
    

    private String imageUrl;

    @ManyToOne()
    private User user;

    public Contact() {
        super();
    }

	public Contact(String contactId,
			@NotBlank(message = "Name is required") @Size(min = 3, max = 50, message = "Name is required between 3 to 50 characters") String name,
			@Email(message = "Invalid Email Address") @NotBlank(message = "Email is required") String email,
			@Size(min = 10, max = 10, message = "10 digits required") String phoneNumber, String imageUrl, User user) {
		super();
		this.contactId = contactId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.imageUrl = imageUrl;
		this.user = user;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    // Getters and Setters
    
    
}
