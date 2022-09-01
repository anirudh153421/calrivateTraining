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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodapp.app.dao.StaffDao;
import com.foodapp.app.dto.Staff;
import com.foodapp.app.service.StaffService;
import com.foodapp.app.util.ResponseStructure;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StaffController {

	@Autowired
	StaffDao staffDao;
	@Autowired
	StaffService staffService;

	@PostMapping("/savestaff/{id}")
	public ResponseEntity<ResponseStructure<Staff>> saveStaff(@RequestBody Staff staff, @PathVariable int id) {
		return staffService.saveStaff(staff, id);
	}

	@GetMapping("/getstaff/{id}")
	public ResponseEntity<ResponseStructure<Staff>> getStaff(@PathVariable int id) {
		return staffService.getStaffById(id);
	}

	@GetMapping("/getstaffs")
	public ResponseEntity<ResponseStructure<List<Staff>>> getStaffs() {
		return staffService.getAllStaffs();
	}

	@PutMapping("/updatestaff/{id}/{mid}")
	public ResponseEntity<ResponseStructure<Staff>> updateStaff(@RequestBody Staff staff, @PathVariable int id,@PathVariable int mid) {
		return staffService.updateStaff(staff, id,mid);
	}

	@DeleteMapping("/deletestaff/{id}")
	public ResponseEntity<ResponseStructure<Staff>> deleteStaff(@PathVariable int id) {
		return staffService.deleteStaff(id);
	}

	@GetMapping("/loginstaff/{email}/{password}")
	public ResponseEntity<ResponseStructure<Staff>> loginStaff(@PathVariable String email,
			@PathVariable String password) {
		return staffService.login(email, password);
	}
@GetMapping("/getstaffbymid/{id}")
public ResponseEntity<ResponseStructure<List<Staff>>> getStaffsByManagerid(@PathVariable int id) {
	return staffService.getStaffByManager(id);
			
}
}
