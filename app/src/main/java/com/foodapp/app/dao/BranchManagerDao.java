package com.foodapp.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodapp.app.dto.BranchManager;
import com.foodapp.app.repository.BranchManagerRepository;
import com.foodapp.app.service.InvalidCredentialsException;

@Repository
public class BranchManagerDao {

	@Autowired
	BranchManagerRepository branchManagerRepository;

	public BranchManager saveBranchManager(BranchManager branchManager) {
		return branchManagerRepository.save(branchManager);
	}

	public Optional<BranchManager> findBranchManagerById(int id) {
		return branchManagerRepository.findById(id);
	}

	public List<BranchManager> findAllBranchManagers() {
		return branchManagerRepository.findAll();
	}

	public BranchManager updateBranchManager(BranchManager branchManager, int id) {
		Optional<BranchManager> optional = findBranchManagerById(id);
		BranchManager branchManager2 = optional.get();
		branchManager2.setName(branchManager.getName());
		branchManager2.setBranch(branchManager.getBranch());
		branchManager2.setEmail(branchManager.getEmail());
		branchManager2.setAdmin(branchManager.getAdmin());
		branchManager2.setRole(branchManager.getRole());
		branchManager2.setStaffs(branchManager.getStaffs());
		branchManagerRepository.save(branchManager2);
		return branchManager2;
	}

	public Optional<BranchManager> deleteBranchManager(int id) {
		Optional<BranchManager> optional = findBranchManagerById(id);
		if (optional.isEmpty()) {
			return null;
		} else {
			branchManagerRepository.delete(optional.get());
			return optional;
		}
	}

	public long getManagerCount() {
		return branchManagerRepository.count();
	}

	public List<BranchManager> getPassword(String email) {
		try {
			return branchManagerRepository.findByEmail(email);
		}
		catch(InvalidCredentialsException e) {
			throw new InvalidCredentialsException();
		}
		
	}

}
