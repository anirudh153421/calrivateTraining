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

import com.foodapp.app.dao.ItemDao;
import com.foodapp.app.dto.Item;
import com.foodapp.app.service.ItemService;
import com.foodapp.app.util.ResponseStructure;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ItemController {
	
	@Autowired
	ItemDao itemDao;
	@Autowired
	ItemService itemService;
	
	@PostMapping("/saveitem")
	public ResponseEntity<ResponseStructure<Item>> saveItem(@RequestBody Item item) {
		return itemService.saveItem(item);
	}
	
	@GetMapping("/getitem/{id}")
	public ResponseEntity<ResponseStructure<Item>> getItem(@PathVariable int id) {
		return itemService.getItemById(id);
	}
	
	@GetMapping("/getitems")
	public ResponseEntity<ResponseStructure<List<Item>>> getItems(){
		return itemService.getAllItems();
	}
	
	@PutMapping("/updateitem/{id}")
	public ResponseEntity<ResponseStructure<Item>> updateItem(@RequestBody Item item,@PathVariable int id) {
		return itemService.updateItem(item, id);
	}
	
	@DeleteMapping("/deleteitem/{id}")
	public ResponseEntity<ResponseStructure<Item>> deleteItem(@PathVariable  int id) {
		return itemService.deleteItem(id);
	}

}
