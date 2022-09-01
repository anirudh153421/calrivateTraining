package com.foodapp.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodapp.app.dto.User;
import com.foodapp.app.repository.UsersRepository;

@Repository
public class UserDao {
	
	@Autowired
	UsersRepository usersRepository;
	
	//Save new User
	public User saveUser(User user) {
		return usersRepository.save(user);
	}
	
	public Optional<User> findUserById(int id){
		return usersRepository.findById(id);
	}
	
	public List<User> findAllUsers(){
		return usersRepository.findAll();
	}
	
	public User updateUser(User user,int id) {
		Optional<User> optional=findUserById(id);
		User user2=optional.get();
		user2.setEmail(user.getEmail());
		user2.setFoodOrders(user.getFoodOrders());
		user2.setName(user.getName());
		user2.setRole(user.getRole());
		usersRepository.save(user2);
		return user2;
	}
	
	public Optional<User> deleteUser(int id){
		Optional<User> optional=findUserById(id);
		if(optional.isEmpty()) {
			return null;
		}
		else
		{
			usersRepository.delete(optional.get());
			return optional;
		}
	}
public long getCustomerCount() {
	return usersRepository.count();
}
}
