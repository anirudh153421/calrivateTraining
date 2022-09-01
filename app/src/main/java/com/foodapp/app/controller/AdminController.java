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

import com.foodapp.app.dao.AdminDao;
import com.foodapp.app.dto.Admin;
import com.foodapp.app.service.AdminService;
import com.foodapp.app.util.ResponseStructure;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@Autowired
	AdminDao adminDao;
	@Autowired
	AdminService adminService;

	@PostMapping("/saveadmin")
	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(@RequestBody Admin admin) {
		return adminService.saveAdmin(admin);
	}

	@GetMapping("/getadmin/{id}")
	public ResponseEntity<ResponseStructure<Admin>> getAdmin(@PathVariable String id) {
		return adminService.getAdminById(Integer.parseInt(id));
	}

	@GetMapping("/getadmins")
	public ResponseEntity<ResponseStructure<List<Admin>>> getAdmins() {
		return adminService.getAllAdmins();
	}

	@PutMapping("/updateadmin/{id}")
	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(@RequestBody Admin admin, @PathVariable int id) {
		return adminService.updateAdmin(admin, id);
	}

	@DeleteMapping("/deleteadmin/{id}")
	public ResponseEntity<ResponseStructure<Admin>> deleteAdmin(@PathVariable int id) {
		return adminService.deleteAdmin(id);
	}

	@GetMapping("/adminlogin/{email}/{password}")
	public ResponseEntity<ResponseStructure<Admin>> loginAdmin(@PathVariable String email,@PathVariable String password) {
		return adminService.login(email, password);
	}

}
