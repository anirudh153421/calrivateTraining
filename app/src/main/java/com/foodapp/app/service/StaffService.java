package com.foodapp.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foodapp.app.dao.BranchManagerDao;
import com.foodapp.app.dao.StaffDao;
import com.foodapp.app.dto.BranchManager;
import com.foodapp.app.dto.Staff;
import com.foodapp.app.exception.DataBaseEmptyException;
import com.foodapp.app.exception.IdNotFoundException;
import com.foodapp.app.util.AES;
import com.foodapp.app.util.ResponseStructure;

@Service
public class StaffService {

	@Autowired
	StaffDao dao;

	@Autowired
	BranchManagerDao branchManagerDao;
	@Autowired
	AES aes;

	public ResponseEntity<ResponseStructure<Staff>> saveStaff(Staff staff, int id) {
		ResponseStructure<Staff> structure = new ResponseStructure<>();
		staff.setPassword(AES.encrypt(staff.getPassword()));
		staff.setRole("staff");
		BranchManager branchManager = branchManagerDao.findBranchManagerById(id).get();
		staff.setBranchManager(branchManager);
		structure.setMessage("Staff Saved Successfully");
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setData(dao.saveStaff(staff));
		return new ResponseEntity<>(structure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Staff>> deleteStaff(int id) {
		if (dao.getStaffCount() == 0)
			throw new DataBaseEmptyException();
		Optional<Staff> optional = dao.deleteStaff(id);
		ResponseStructure<Staff> structure = new ResponseStructure<>();
		if (optional == null) {
			throw new IdNotFoundException();
		} else {
			structure.setMessage("deleted Successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(optional.get());
			return new ResponseEntity<ResponseStructure<Staff>>(structure, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseStructure<Staff>> getStaffById(int id) {
		if (dao.getStaffCount() == 0)
			throw new DataBaseEmptyException();
		Optional<Staff> optional = dao.findStaffById(id);
		ResponseStructure<Staff> structure = new ResponseStructure<>();
		if (optional.isEmpty()) {
			throw new IdNotFoundException();
		} else {
			structure.setMessage("Staff Found");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(optional.get());
			return new ResponseEntity<ResponseStructure<Staff>>(structure, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseStructure<List<Staff>>> getAllStaffs() {
		if (dao.getStaffCount() == 0)
			throw new DataBaseEmptyException();
		ResponseStructure<List<Staff>> structure = new ResponseStructure<>();
		List<Staff> list = dao.findAllStaffs();
		if (list.isEmpty())
			throw new IdNotFoundException();
		else {
			structure.setMessage("fetched Successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(list);
			return new ResponseEntity<ResponseStructure<List<Staff>>>(structure, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseStructure<Staff>> updateStaff(Staff staff, int id,int mid) {
		if (dao.getStaffCount() == 0)
			throw new DataBaseEmptyException();
		staff.setPassword(AES.encrypt(staff.getPassword()));
		staff.setBranchManager(branchManagerDao.findBranchManagerById(mid).get());
		Staff staff2 = dao.updateStaff(staff, id);
		ResponseStructure<Staff> structure = new ResponseStructure<>();
		if (staff2 != null) {
			structure.setMessage("Updated Successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(staff);
			return new ResponseEntity<ResponseStructure<Staff>>(structure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException();
		}
	}

	public ResponseEntity<ResponseStructure<Staff>> login(String email, String password) {
		if(dao.getPassword(email).size()>0) {
			Staff s=dao.getPassword(email).get(0);
			String pass = AES.decrypt(s.getPassword());
			ResponseStructure<Staff> structure = new ResponseStructure<>();
			if (pass.equals(null))
				throw new InvalidCredentialsException();
			if(password.equals(pass)) {
				structure.setMessage("Staff Logged in Successfully");
				structure.setStatus(HttpStatus.OK.value());
				structure.setData(s);
				return new ResponseEntity<ResponseStructure<Staff>>(structure, HttpStatus.OK);
			}
			else {
				structure.setMessage("invalid password");
				structure.setStatus(HttpStatus.OK.value());
				structure.setData(null);
				return new ResponseEntity<ResponseStructure<Staff>>(structure, HttpStatus.NOT_FOUND);
			}
		}
		else {
			throw new InvalidCredentialsException();
		}
	}
	public ResponseEntity<ResponseStructure<List<Staff>>> getStaffByManager(int id) {
		if(branchManagerDao.findBranchManagerById(id).isEmpty())
			throw new IdNotFoundException();
		BranchManager manager=branchManagerDao.findBranchManagerById(id).get();
		if(dao.getStaffByManager(manager).equals(null))
			throw new DataBaseEmptyException();
		ResponseStructure<List<Staff>> structure = new ResponseStructure<>();
		structure.setMessage("fetched Successfully");
		structure.setStatus(HttpStatus.OK.value());
		structure.setData(dao.getStaffByManager(manager));
		return new ResponseEntity<ResponseStructure<List<Staff>>>(structure, HttpStatus.OK);
	}
}
