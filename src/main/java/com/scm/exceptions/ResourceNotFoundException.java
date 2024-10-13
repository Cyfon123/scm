package com.scm.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	
	
	public ResourceNotFoundException() {
		super("User not Found");
		
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

	
}
