package com.foodapp.app.exception;

public class IdNotFoundException extends RuntimeException {
	String message="Id not Found";
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

}
