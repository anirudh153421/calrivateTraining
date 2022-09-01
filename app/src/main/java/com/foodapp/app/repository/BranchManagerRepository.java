package com.foodapp.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodapp.app.dto.BranchManager;

public interface BranchManagerRepository extends JpaRepository<BranchManager, Integer> {
List<BranchManager> findByEmail(String email);
}
