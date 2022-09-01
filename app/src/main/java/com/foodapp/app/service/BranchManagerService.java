package com.foodapp.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foodapp.app.dao.AdminDao;
import com.foodapp.app.dao.BranchManagerDao;
import com.foodapp.app.dto.Admin;
import com.foodapp.app.dto.BranchManager;
import com.foodapp.app.exception.DataBaseEmptyException;
import com.foodapp.app.exception.IdNotFoundException;
import com.foodapp.app.util.AES;
import com.foodapp.app.util.ResponseStructure;

@Service
public class BranchManagerService {

	@Autowired
	BranchManagerDao dao;

	@Autowired
	AdminDao adminDao;
@Autowired
AES aes;
	public ResponseEntity<ResponseStructure<BranchManager>> saveBranchManager(BranchManager branchManager, int id) {
		ResponseStructure<BranchManager> structure = new ResponseStructure<>();
		branchManager.setPassword(AES.encrypt(branchManager.getPassword()));
		branchManager.setRole("manager");
		Admin admin = adminDao.findAdminById(id).get();
		branchManager.setAdmin(admin);
		structure.setMessage("BranchManager Saved Successfully");
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setData(dao.saveBranchManager(branchManager));
		return new ResponseEntity<>(structure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<BranchManager>> deleteBranchManager(int id) {
		if (dao.getManagerCount() == 0)
			throw new DataBaseEmptyException();
		Optional<BranchManager> optional = dao.deleteBranchManager(id);
		ResponseStructure<BranchManager> structure = new ResponseStructure<>();
		if (optional == null) {
			throw new IdNotFoundException();
		} else {
			structure.setMessage("deleted Successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(optional.get());
			return new ResponseEntity<ResponseStructure<BranchManager>>(structure, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseStructure<BranchManager>> getBranchManagerById(int id) {
		if (dao.getManagerCount() == 0)
			throw new DataBaseEmptyException();
		Optional<BranchManager> optional = dao.findBranchManagerById(id);
		ResponseStructure<BranchManager> structure = new ResponseStructure<>();
		if (optional.isEmpty()) {
			throw new IdNotFoundException();
		} else {
			structure.setMessage("BranchManager Found");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(optional.get());
			return new ResponseEntity<ResponseStructure<BranchManager>>(structure, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseStructure<List<BranchManager>>> getAllBranchManagers() {
		if (dao.getManagerCount() == 0)
			throw new DataBaseEmptyException();
		ResponseStructure<List<BranchManager>> structure = new ResponseStructure<>();
		List<BranchManager> list = dao.findAllBranchManagers();
		if (list.isEmpty())
			throw new IdNotFoundException();
		else {
			structure.setMessage("fetched Successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(list);
			return new ResponseEntity<ResponseStructure<List<BranchManager>>>(structure, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseStructure<BranchManager>> updateBranchManager(BranchManager branchManager, int id,int aid) {
		if (dao.getManagerCount() == 0)
			throw new DataBaseEmptyException();
		branchManager.setRole("manager");
		branchManager.setAdmin(adminDao.findAdminById(aid).get());
		branchManager.setPassword(AES.encrypt(branchManager.getPassword()));
		BranchManager branchManager2 = dao.updateBranchManager(branchManager, id);
		ResponseStructure<BranchManager> structure = new ResponseStructure<>();
		if (branchManager2 != null) {
			structure.setMessage("Updated Successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(branchManager);
			return new ResponseEntity<ResponseStructure<BranchManager>>(structure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException();
		}
	}

	public ResponseEntity<ResponseStructure<BranchManager>> login(String email, String password) {
		if(dao.getPassword(email).size()>0) {
			BranchManager b=dao.getPassword(email).get(0);
			if(b==null)
				throw new InvalidCredentialsException();
						String pass = AES.decrypt(b.getPassword());
						System.out.println(pass);
						ResponseStructure<BranchManager> structure = new ResponseStructure<>();
						if (pass.equals(null))
							throw new InvalidCredentialsException();
						if(password.equals(pass)) {
							structure.setMessage("Manager Logged in Successfully");
							structure.setStatus(HttpStatus.OK.value());
							structure.setData(b);
							return new ResponseEntity<ResponseStructure<BranchManager>>(structure, HttpStatus.OK);
						}
						else {
							structure.setMessage("invalid password");
							structure.setStatus(HttpStatus.OK.value());
							structure.setData(null);
							return new ResponseEntity<ResponseStructure<BranchManager>>(structure, HttpStatus.NOT_FOUND);
						}
		}
		else {
			throw new InvalidCredentialsException();
		}

	}

}
