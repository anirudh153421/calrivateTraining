package com.foodapp.app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.foodapp.app.dao.UserDao;
import com.foodapp.app.dto.User;
import com.foodapp.app.service.UserService;
import com.foodapp.app.util.ResponseStructure;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {
	
	@Autowired
	UserDao userDao;
	@Autowired
	UserService userService;
	
	@PostMapping("/saveuser")
	public ResponseEntity<ResponseStructure<User>> saveUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	
	@GetMapping("/getuser/{id}")
	public ResponseEntity<ResponseStructure<User>> getUser(@PathVariable int id) {
		return userService.getUserById(id);
	}
	
	@GetMapping("/getusers")
	public ResponseEntity<ResponseStructure<List<User>>> getUsers(){
		return userService.getAllUsers();
	}
	
	@PutMapping("/updateuser/{id}")
	public ResponseEntity<ResponseStructure<User>> updateUser(@RequestBody User user,@PathVariable int id) {
		return userService.updateUser(user, id);
	}
	
	@DeleteMapping("/deleteuser/{id}")
	public ResponseEntity<ResponseStructure<User>> deleteUser(@PathVariable  int id) {
		return userService.deleteUser(id);
	}

}
