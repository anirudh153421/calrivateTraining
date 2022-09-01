package com.foodapp.app.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodapp.app.dao.FoodOrderDao;
import com.foodapp.app.dto.FoodOrder;
import com.foodapp.app.email.EmailDetails;
import com.foodapp.app.email.EmailServiceImpl;
import com.foodapp.app.service.FoodOrderService;
import com.foodapp.app.util.ResponseStructure;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FoodOrderController {
	
	@Autowired
	FoodOrderDao foodOrderDao;
	@Autowired
	FoodOrderService foodOrderService;
	@Autowired
	EmailServiceImpl emailServiceImpl;
	@PostMapping("/savefoodOrder/staff:{id}/user:{id2}")
	public ResponseEntity<ResponseStructure<FoodOrder>> saveFoodOrder(@RequestBody FoodOrder order,@PathVariable int id,@PathVariable int id2,@RequestParam ArrayList<Integer> ids) {
		return foodOrderService.saveFoodOrder(order,id,id2,ids);
	}
	
	@GetMapping("/getfoodOrder/{id}")
	public ResponseEntity<ResponseStructure<FoodOrder>> getFoodOrder(@PathVariable int id) {
		return foodOrderService.getFoodOrderById(id);
	}
	
	@GetMapping("/getfoodOrders")
	public ResponseEntity<ResponseStructure<List<FoodOrder>>> getFoodOrders(){
		return foodOrderService.getAllFoodOrders();
	}
	
	@PutMapping("/updatefoodOrder/{id}")
	public ResponseEntity<ResponseStructure<FoodOrder>> updateFoodOrder(@RequestBody FoodOrder foodOrder,@PathVariable int id,@RequestParam List<Integer> ids) {
		return foodOrderService.updateFoodOrder(foodOrder, id);
	}
	
	@DeleteMapping("/deletefoodOrder/{id}")
	public ResponseEntity<ResponseStructure<FoodOrder>> deleteFoodOrder(@PathVariable  int id) {
		return foodOrderService.deleteFoodOrder(id);
	}
	
	@GetMapping ("/mailfoodOrder/{id}")
	public ResponseEntity<ResponseStructure<EmailDetails>> sendMail(@PathVariable int id)
    {
		return emailServiceImpl.sendSimpleMail(id);
    }

}
