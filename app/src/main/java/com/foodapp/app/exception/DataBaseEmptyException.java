package com.foodapp.app.exception;

public class DataBaseEmptyException extends RuntimeException {
@Override
public String getMessage() {
	return "Database is empty";
}
}
