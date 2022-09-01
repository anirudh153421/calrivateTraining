package com.foodapp.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foodapp.app.dao.AdminDao;
import com.foodapp.app.dto.Admin;
import com.foodapp.app.exception.DataBaseEmptyException;
import com.foodapp.app.exception.IdNotFoundException;
import com.foodapp.app.util.AES;
import com.foodapp.app.util.ResponseStructure;

@Service
public class AdminService {

	@Autowired
	AdminDao dao;
@Autowired
AES aes;
	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(Admin admin) {
		ResponseStructure<Admin> structure = new ResponseStructure<>();
		admin.setRole("admin");
		admin.setPassword(AES.encrypt(admin.getPassword()));
		structure.setMessage("Admin Saved Successfully");
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setData(dao.saveAdmin(admin));
		return new ResponseEntity<>(structure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Admin>> deleteAdmin(int id) {
		Optional<Admin> optional = dao.deleteAdmin(id);
		ResponseStructure<Admin> structure = new ResponseStructure<>();
		if (optional == null) {
			throw new IdNotFoundException();
		} else {
			structure.setMessage("deleted Successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(optional.get());
			return new ResponseEntity<ResponseStructure<Admin>>(structure, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseStructure<Admin>> getAdminById(int id) {
		if (dao.getAdminCount() == 0)
			throw new DataBaseEmptyException();
		Optional<Admin> optional = dao.findAdminById(id);
		ResponseStructure<Admin> structure = new ResponseStructure<>();
		if (optional.isEmpty()) {
			throw new IdNotFoundException();
		} else {
			structure.setMessage("Admin Found");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(optional.get());
			return new ResponseEntity<ResponseStructure<Admin>>(structure, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseStructure<List<Admin>>> getAllAdmins() {
		if (dao.getAdminCount() == 0)
			throw new DataBaseEmptyException();
		ResponseStructure<List<Admin>> structure = new ResponseStructure<>();
		List<Admin> list = dao.findAllAdmins();
		if (list.isEmpty())
			throw new IdNotFoundException();
		else {
			structure.setMessage("fetched Successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(list);
			return new ResponseEntity<ResponseStructure<List<Admin>>>(structure, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(Admin admin, int id) {
		if (dao.getAdminCount() == 0)
			throw new DataBaseEmptyException();
		admin.setPassword(AES.encrypt(admin.getPassword()));
		Admin admin2 = dao.updateAdmin(admin, id);
		ResponseStructure<Admin> structure = new ResponseStructure<>();
		if (admin2 != null) {
			admin2.setPassword(AES.decrypt(admin2.getPassword()));
		
			structure.setMessage("Updated Successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(admin2);
			return new ResponseEntity<ResponseStructure<Admin>>(structure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException();
		}
	}

	public ResponseEntity<ResponseStructure<Admin>> login(String email, String password) {
		if(dao.getPassword(email).size()>0) {
			System.out.println(password);
			Admin a=dao.getPassword(email).get(0);
			if(a.equals(null))
				throw new IdNotFoundException();
			System.out.println(a.getPassword());
			String pass = AES.decrypt(a.getPassword());
			System.out.println(pass);
			if (pass.equals(null))
				throw new InvalidCredentialsException();
			ResponseStructure<Admin> structure = new ResponseStructure<>();
			if(password.equals(pass)) {
				structure.setMessage( " admin Logged in Successfully");
				structure.setStatus(HttpStatus.OK.value());
				structure.setData(a);
				return new ResponseEntity<ResponseStructure<Admin>>(structure, HttpStatus.OK);
			}
			else {
				structure.setMessage("invalid password");
				structure.setStatus(HttpStatus.OK.value());
				structure.setData(null);
				return new ResponseEntity<ResponseStructure<Admin>>(structure, HttpStatus.NOT_FOUND);
			}
		}
		else {
			throw new InvalidCredentialsException();
		}
		
	}

}
