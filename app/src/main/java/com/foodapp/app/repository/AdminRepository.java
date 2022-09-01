package com.foodapp.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodapp.app.dto.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
List<Admin> findByEmail(String email);
}
