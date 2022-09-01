package com.foodapp.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.foodapp.app.dao.MenuDao;
import com.foodapp.app.dto.Menu;
import com.foodapp.app.service.MenuService;
import com.foodapp.app.util.ResponseStructure;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MenuController {
	
	@Autowired
	MenuDao menuDao;
	@Autowired
	MenuService menuService;
	
	@PostMapping("/savemenu/{id}")
	public ResponseEntity<ResponseStructure<Menu>> saveMenu(@RequestBody Menu menu,@PathVariable int id) {
		return menuService.saveMenu(menu,id);
	}
	
	@GetMapping("/getmenu/{id}")
	public ResponseEntity<ResponseStructure<Menu>> getMenu(@PathVariable int id) {
		return menuService.getMenuById(id);
	}
	
	@GetMapping("/getmenus")
	public ResponseEntity<ResponseStructure<List<Menu>>> getMenus(){
		return menuService.getAllMenus();
	}
	
	@PutMapping("/updatemenu/{id}")
	public ResponseEntity<ResponseStructure<Menu>> updateMenu(@RequestBody Menu menu,@PathVariable int id) {
		return menuService.updateMenu(menu, id);
	}
	
	@DeleteMapping("/deletemenu/{id}")
	public ResponseEntity<ResponseStructure<Menu>> deleteMenu(@PathVariable  int id) {
		return menuService.deleteMenu(id);
	}

}

