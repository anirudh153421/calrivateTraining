package com.foodapp.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodapp.app.dto.FoodOrder;
import com.foodapp.app.repository.FoodOrderRepository;

@Repository
public class FoodOrderDao {
	
		@Autowired
		FoodOrderRepository foodOrderRepository ;
		
		public FoodOrder saveFoodOrder(FoodOrder foodOrder) {
			return foodOrderRepository.save(foodOrder);
		}
		
		public Optional<FoodOrder> findFoodOrderById(int id){
			return foodOrderRepository.findById(id);
		}
		
		public List<FoodOrder> findAllFoodOrders(){
			return foodOrderRepository.findAll();
		}
		
		public FoodOrder updateFoodOrder(FoodOrder foodOrder,int id) {
			Optional<FoodOrder> optional=findFoodOrderById(id);
			FoodOrder foodOrder2=optional.get();
			foodOrder2.setBillingAmount(foodOrder.getBillingAmount());
			foodOrder2.setItems(foodOrder.getItems());
			foodOrder2.setStatus(foodOrder.isStatus());
			foodOrder2.setUser(foodOrder.getUser());
			foodOrderRepository.save(foodOrder2);
			return foodOrder2;
		}
		
		public Optional<FoodOrder> deleteFoodOrder(int id){
			Optional<FoodOrder> optional=findFoodOrderById(id);
			if(optional.isEmpty()) {
				return null;
			}
			else
			{
				foodOrderRepository.delete(optional.get());
				return optional;
			}
		}
public long getOrderCount() {
	return foodOrderRepository.count();
}
}
