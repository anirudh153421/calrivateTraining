package com.foodapp.app.exception;

public class CustomerFieldEmptyException extends RuntimeException {
@Override
public String getMessage() {
	return "All fields must be filled";
}
}
