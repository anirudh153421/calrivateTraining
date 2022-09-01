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

import com.foodapp.app.dao.BranchManagerDao;
import com.foodapp.app.dto.BranchManager;
import com.foodapp.app.service.BranchManagerService;
import com.foodapp.app.util.ResponseStructure;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class BranchManagerController {

	@Autowired
	BranchManagerDao branchManagerDao;
	@Autowired
	BranchManagerService branchManagerService;

	@PostMapping("/savebranchManager/{id}")
	public ResponseEntity<ResponseStructure<BranchManager>> saveBranchManager(@RequestBody BranchManager branchManager,
			@PathVariable int id) {
		return branchManagerService.saveBranchManager(branchManager, id);
	}

	@GetMapping("/getbranchManager/{id}")
	public ResponseEntity<ResponseStructure<BranchManager>> getBranchManager(@PathVariable int id) {
		return branchManagerService.getBranchManagerById(id);
	}

	@GetMapping("/getbranchManagers")
	public ResponseEntity<ResponseStructure<List<BranchManager>>> getBranchManagers() {
		return branchManagerService.getAllBranchManagers();
	}

	@PutMapping("/updatebranchManager/{id}/{aid}")
	public ResponseEntity<ResponseStructure<BranchManager>> updateBranchManager(
			@RequestBody BranchManager branchManager, @PathVariable int id,@PathVariable int aid) {
		return branchManagerService.updateBranchManager(branchManager, id,aid);
	}

	@DeleteMapping("/deletebranchManager/{id}")
	public ResponseEntity<ResponseStructure<BranchManager>> deleteBranchManager(@PathVariable int id) {
		return branchManagerService.deleteBranchManager(id);
	}

	@GetMapping("/managerlogin/{email}/{password}")
	public ResponseEntity<ResponseStructure<BranchManager>> loginManager(@PathVariable String email,@PathVariable String password) {
		return branchManagerService.login(email, password);
	}

}
