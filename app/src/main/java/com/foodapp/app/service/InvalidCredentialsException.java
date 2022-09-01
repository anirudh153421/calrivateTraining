package com.foodapp.app.service;

public class InvalidCredentialsException extends RuntimeException {
@Override
public String getMessage() {
	return "Invalid credentials";
}
}
