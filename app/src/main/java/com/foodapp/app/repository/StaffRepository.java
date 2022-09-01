package com.foodapp.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodapp.app.dto.BranchManager;
import com.foodapp.app.dto.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
	List<Staff> findByEmail(String email);
	List<Staff> findByBranchManager(BranchManager manager);
}
