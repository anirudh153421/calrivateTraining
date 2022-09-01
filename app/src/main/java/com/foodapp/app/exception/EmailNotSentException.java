package com.foodapp.app.exception;

public class EmailNotSentException extends RuntimeException {
	String message="Email Not Sent! Unsucessful";
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

}