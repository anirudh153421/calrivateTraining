package com.foodapp.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodapp.app.dto.User;

public interface UsersRepository extends JpaRepository<User, Integer> {

}
