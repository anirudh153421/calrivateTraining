package com.foodapp.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.foodapp.app.service.InvalidCredentialsException;
import com.foodapp.app.util.ResponseStructure;


@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
	
@ExceptionHandler(IdNotFoundException.class)
public ResponseEntity<ResponseStructure<String>> idNotFoundException(IdNotFoundException exception){
    ResponseStructure<String> structure=new ResponseStructure<>();
    structure.setMessage(exception.getMessage());
    structure.setStatus(HttpStatus.NOT_FOUND.value());
    structure.setData("No such ID in Database");
    return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
}

@ExceptionHandler(EmailNotSentException.class)
public ResponseEntity<ResponseStructure<String>> emailNotSentException(EmailNotSentException exception){
    ResponseStructure<String> structure=new ResponseStructure<>();
    structure.setMessage(exception.getMessage());
    structure.setStatus(HttpStatus.NOT_FOUND.value());
    structure.setData("Email Could Not Be Sent");
    return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
}
@ExceptionHandler(DataBaseEmptyException.class)
public ResponseEntity<ResponseStructure<String>> emailNotSentException(DataBaseEmptyException exception){
    ResponseStructure<String> structure=new ResponseStructure<>();
    structure.setMessage(exception.getMessage());
    structure.setStatus(HttpStatus.NOT_FOUND.value());
    structure.setData("DataBase is Empty");
    return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
}
@ExceptionHandler(CustomerFieldEmptyException.class)
public ResponseEntity<ResponseStructure<String>> emailNotSentException(CustomerFieldEmptyException exception){
    ResponseStructure<String> structure=new ResponseStructure<>();
    structure.setMessage(exception.getMessage());
    structure.setStatus(HttpStatus.NOT_FOUND.value());
    structure.setData("Customer could not be saved");
    return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
}
@ExceptionHandler(InvalidCredentialsException.class)
public ResponseEntity<ResponseStructure<String>> emailNotSentException(InvalidCredentialsException exception){
    ResponseStructure<String> structure=new ResponseStructure<>();
    structure.setMessage(exception.getMessage());
    structure.setStatus(HttpStatus.NOT_FOUND.value());
    structure.setData("admin cannot login");
    return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
}
}
