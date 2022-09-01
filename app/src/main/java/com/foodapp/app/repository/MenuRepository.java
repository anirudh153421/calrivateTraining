package com.foodapp.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodapp.app.dto.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

}
