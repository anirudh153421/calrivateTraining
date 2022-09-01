package com.foodapp.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foodapp.app.dao.UserDao;
import com.foodapp.app.dto.User;
import com.foodapp.app.exception.CustomerFieldEmptyException;
import com.foodapp.app.exception.IdNotFoundException;
import com.foodapp.app.util.AES;
import com.foodapp.app.util.ResponseStructure;

@Service
public class UserService {
	
	@Autowired
    UserDao dao;

    public ResponseEntity<ResponseStructure<User>> saveUser(User user) {
    	if(user.getEmail()==null || user.getName()==null || user.getPassword()==null)
    		throw new CustomerFieldEmptyException();
        ResponseStructure<User> structure = new ResponseStructure<>();
        user.setPassword(AES.encrypt(user.getPassword()));
        user.setRole("user");
        structure.setMessage("User Saved Successfully");
        structure.setStatus(HttpStatus.CREATED.value());
        structure.setData(dao.saveUser(user));
        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<User>> deleteUser(int id) {
        Optional<User> optional = dao.deleteUser(id);
        ResponseStructure<User> structure = new ResponseStructure<>();
        if (optional == null) {
            throw new IdNotFoundException();
        } else {
            structure.setMessage("deleted Successfully");
            structure.setStatus(HttpStatus.OK.value());
            structure.setData(optional.get());
            return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseStructure<User>> getUserById(int id) {
        Optional<User> optional = dao.findUserById(id);
        ResponseStructure<User> structure = new ResponseStructure<>();
        if (optional.isEmpty()) {
            throw new IdNotFoundException();
        } else {
            structure.setMessage("User Found");
            structure.setStatus(HttpStatus.OK.value());
            structure.setData(optional.get());
            return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseStructure<List<User>>> getAllUsers() {
        ResponseStructure<List<User>> structure = new ResponseStructure<>();
        List<User> list = dao.findAllUsers();
        if (list.isEmpty())
            throw new IdNotFoundException();
        else {
            structure.setMessage("fetched Successfully");
            structure.setStatus(HttpStatus.OK.value());
            structure.setData(list);
            return new ResponseEntity<ResponseStructure<List<User>>>(structure, HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseStructure<User>> updateUser(User user, int id) {
        User user2 = dao.updateUser(user, id);
        ResponseStructure<User> structure = new ResponseStructure<>();
        if (user2 != null) {
            structure.setMessage("Updated Successfully");
            structure.setStatus(HttpStatus.OK.value());
            structure.setData(user);
            return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.OK);
        } else {
            throw new IdNotFoundException();
        }
    }
}

