package com.scm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.Message;
import com.scm.services.CloudinaryService;
import com.scm.services.ContactService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private CloudinaryService cloudinaryService;
	
	@Autowired
	private Cloudinary cloudinary;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ContactService contactService;
	
	@GetMapping()
	public String base()
	{
	      return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login(Model model)
	{
		User user = new User();
		model.addAttribute("user", user);
		return "login";
	}

	@GetMapping("/signUp")
	public String signUp(Model model)
	{
		User user = new User();
		model.addAttribute("user", user);
		return "signUp";
	}
	
	@PostMapping("do-register")
	public String doRegister(@Valid @ModelAttribute User user, BindingResult results, HttpSession session, @RequestParam("image") MultipartFile file, Model model) {
	    // Check for validation errors
	    if (results.hasErrors()) {
	        model.addAttribute("user", user);
	        return "signUp";  // Return back to the signup page with errors
	    }

	    // Check if the user already exists
	    User alreadyExists = userService.getUserByEmail(user.getEmail());
	    if (alreadyExists != null) {
	        Message message = new Message("User already exists!", "blue");
	        session.setAttribute("message", message);
	        return "redirect:/login";  // Redirect to login if user exists
	    }

	    // Handle file upload and user creation
	    try {
	        Map data = this.cloudinaryService.uploadFile(file);
	        user.setImageUrl((String) data.get("secure_url"));
	        userService.create(user);  // Save new user to the database
	    } catch (IOException e) {
	        // If image upload fails, provide feedback
	        Message message = new Message("Image upload failed. Please try again.", "red");
	        session.setAttribute("message", message);
	        return "signUp";  // Return to signup with the error message
	    }

	    Message successMessage = new Message("Registration successful!", "green");
	    session.setAttribute("message", successMessage);
	    return "redirect:/login";
	}

	
	@PostMapping("do-login")
	public String doLogin(@ModelAttribute User user, Model model, HttpSession session) {
	    User existUser = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());

	    if (userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword())==null && userService.getUserByPhoneNumberAndPassword(user.getEmail(), user.getPassword())==null) {
	    	 model.addAttribute("errorMessage", "Invalid credentials. Please try again.");
 	          return "login"; 
	    }
	    if(existUser != null)
	    {
	    	  session.setAttribute("user", existUser); 
	    	  return "redirect:/profile"; 
	    }
	    existUser = userService.getUserByPhoneNumberAndPassword(user.getEmail(), user.getPassword());
	    session.setAttribute("user", existUser); 
	    return "redirect:/profile"; 
	   
	}

	
	@GetMapping("/profile")
	public String userInfo(Model model, HttpSession session) {
	    User user = (User) session.getAttribute("user");
	    if (user == null) {
	        return "redirect:/login";  // Redirect to login if session is invalid
	    }

	    model.addAttribute("user", user);
	    return "profile";
	}


	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate();  // Invalidate session on logout
	    return "redirect:/login";  // Redirect to login page after logout
	}

	
	// display addContact page
	
	@GetMapping("/addContact")
	public String addContact(Model model,HttpSession session)
	{
		if(session.getAttribute("user") == null)
			return "redirect:/login";
		Contact contact = new Contact();
		model.addAttribute("contact", contact);
		return "addContact";
	}
	
	// add new contact in database
	
	@PostMapping("/do-addContact")
	public String doAddContact(@ModelAttribute Contact contact,BindingResult results, Model model, HttpSession session, @RequestParam("image") MultipartFile imageFile) {
	    User loggedInUser = (User) session.getAttribute("user");
	    
	    // Validate required fields
	    if (results.hasErrors()) {
	        model.addAttribute("errorMessage", "Name, email, and phone number are required.");
	        return "addContact";  // Stay on the page and show error
	    }
	    

	    if(imageFile != null && !imageFile.isEmpty())
	    {
	    try 
	    {
	        Map data = this.cloudinaryService.uploadFile(imageFile);
	        contact.setImageUrl((String) data.get("secure_url"));
	    } 
	    catch (IOException e) 
	    {
	        model.addAttribute("errorMessage", "Image upload failed. Please try again.");
	        return "addContact";
	    }
	    }


	    contact.setUser(loggedInUser);  // Associate contact with the logged-in user
	    contactService.create(contact);  // Save the contact
//	    return "redirect:/profile/contacts";  // Redirect to contacts list
	    return "redirect:/profile/contacts";  // Redirect to contacts list
	}


	
	@GetMapping("/profile/contacts")
	public String userContacts( Model model, HttpSession session)
	{
		// if any one try manually then it goes on login if user is null
		if(session.getAttribute("user") == null)
			return "redirect:/login";
		User loggedInuser = (User)session.getAttribute("user");
		String userId = loggedInuser.getUserId();
		System.out.println("UserId is :- " + userId);
		List<Contact> contacts = contactService.getContactsByUser(loggedInuser);
		model.addAttribute("contacts",contacts);
		return "userContacts";
	}
	
    // delete contact in database
	 @GetMapping("/profile/contacts/delete/{contactId}")
	    public String deleteContact(@PathVariable String contactId, Model model) {
	        contactService.deleteContact(contactId);

	        return "redirect:/profile/contacts"; // Return the name of the view for editing the contact
	    }
//	 /profile/contacts/edit/{contactId}(contactId=${contact.contactId}
	 
	// Display the contact edit form
	 @GetMapping("/profile/contacts/edit/{contactId}")
	 public String editContact(@PathVariable String contactId, Model model) {
	    Contact contact = contactService.getContact(contactId);
	    model.addAttribute("contact", contact);
	    return "editContact"; // Return the edit contact view
	 }

	 // Handle contact update
	 @PostMapping("/do-editContact")
	 public String doEditContact(@ModelAttribute("contact") Contact contact, 
	                             @RequestParam(value = "image", required = false) MultipartFile image, 
	                             HttpSession session, 
	                             Model model) {
	     
	     User loggedInUser = (User) session.getAttribute("user");
	     contact.setUser(loggedInUser);  // Reassociate the updated contact with the logged-in user

	     // Retrieve the existing contact from the database
	     Contact existingContact = contactService.getContact(contact.getContactId());

	     // Check if a new image is uploaded
	     if (image != null && !image.isEmpty()) {
	         try {
	             // Save the new image file (you can add your own logic here)
	        	 System.out.println(existingContact.getImageUrl());
	        	 Map data = this.cloudinaryService.uploadFile(image);
	 	        contact.setImageUrl((String) data.get("secure_url"));  // Set the new image URL
	 	       System.out.println(contact.getImageUrl());
	         } catch (IOException e) {
	             model.addAttribute("errorMessage", "Failed to upload the image.");
	             return "editContact";
	         }
	     } else {
	         // If no new image is uploaded, retain the existing image URL
	         contact.setImageUrl(existingContact.getImageUrl());
	     }

	     // Update contact details
	     contactService.updateContact(contact);

	     return "redirect:/profile/contacts";  // Redirect to contacts list
	 }




	
}
