package com.foodapp.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foodapp.app.dao.FoodOrderDao;
import com.foodapp.app.dao.ItemDao;
import com.foodapp.app.dao.StaffDao;
import com.foodapp.app.dao.UserDao;
import com.foodapp.app.dto.FoodOrder;
import com.foodapp.app.dto.Item;
import com.foodapp.app.exception.DataBaseEmptyException;
import com.foodapp.app.exception.IdNotFoundException;
import com.foodapp.app.util.ResponseStructure;

@Service
public class FoodOrderService {
	@Autowired
    FoodOrderDao dao;
	@Autowired
	ItemDao itemDao;
	@Autowired
	StaffDao staffDao;
	@Autowired
	UserDao userDao;
    public ResponseEntity<ResponseStructure<FoodOrder>> saveFoodOrder(FoodOrder foodOrder,int id,int id2,ArrayList<Integer> ids) {
//    	FoodOrder foodOrder=new FoodOrder();
        ResponseStructure<FoodOrder> structure = new ResponseStructure<>();
        foodOrder.setStaff(staffDao.findStaffById(id).get());
        foodOrder.setUser(userDao.findUserById(id2).get());
        foodOrder.setStatus(false);
        foodOrder.setCustomerName(userDao.findUserById(id2).get().getName());
        foodOrder.setCustomerEmail(userDao.findUserById(id2).get().getEmail());
        List<Item> items=new ArrayList<>();
        long total=0;
        int quantity=0;
        for(int temp=0;temp<ids.size();temp++) {
        	Item i=itemDao.findItemById(ids.get(temp)).get();
        	i.setQuantity(i.getQuantity()-1);
        	items.add(i);
        	total+=i.getPrice();
        	quantity++;
        }
        foodOrder.setBillingAmount(total);
        foodOrder.setQuantity(quantity);
        FoodOrder foodOrder2=dao.saveFoodOrder(foodOrder);
        for(Item i:items) {
        	i.setFoodOrder(foodOrder2);
        	itemDao.saveItem(i);
        }
        structure.setMessage("FoodOrder Saved Successfully");
        structure.setStatus(HttpStatus.CREATED.value());
        structure.setData(foodOrder2);
        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<FoodOrder>> deleteFoodOrder(int id) {
    	if(dao.getOrderCount()==0)
    		throw new DataBaseEmptyException();
        Optional<FoodOrder> optional = dao.deleteFoodOrder(id);
        ResponseStructure<FoodOrder> structure = new ResponseStructure<>();
        if (optional == null) {
            throw new IdNotFoundException();
        } else {
            structure.setMessage("deleted Successfully");
            structure.setStatus(HttpStatus.OK.value());
            structure.setData(optional.get());
            return new ResponseEntity<ResponseStructure<FoodOrder>>(structure, HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseStructure<FoodOrder>> getFoodOrderById(int id) {
    	if(dao.getOrderCount()==0)
    		throw new DataBaseEmptyException();
        Optional<FoodOrder> optional = dao.findFoodOrderById(id);
        ResponseStructure<FoodOrder> structure = new ResponseStructure<>();
        if (optional.isEmpty()) {
            throw new IdNotFoundException();
        } else {
            structure.setMessage("FoodOrder Found");
            structure.setStatus(HttpStatus.OK.value());
            structure.setData(optional.get());
            return new ResponseEntity<ResponseStructure<FoodOrder>>(structure, HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseStructure<List<FoodOrder>>> getAllFoodOrders() {
    	if(dao.getOrderCount()==0)
    		throw new DataBaseEmptyException();
        ResponseStructure<List<FoodOrder>> structure = new ResponseStructure<>();
        List<FoodOrder> list = dao.findAllFoodOrders();
        if (list.isEmpty())
            throw new IdNotFoundException();
        else {
            structure.setMessage("fetched Successfully");
            structure.setStatus(HttpStatus.OK.value());
            structure.setData(list);
            return new ResponseEntity<ResponseStructure<List<FoodOrder>>>(structure, HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseStructure<FoodOrder>> updateFoodOrder(FoodOrder foodOrder, int id) {
    	if(dao.getOrderCount()==0)
    		throw new DataBaseEmptyException();
        FoodOrder foodOrder2 = dao.updateFoodOrder(foodOrder, id);
        ResponseStructure<FoodOrder> structure = new ResponseStructure<>();
        if (foodOrder2 != null) {
            structure.setMessage("Updated Successfully");
            structure.setStatus(HttpStatus.OK.value());
            structure.setData(foodOrder);
            return new ResponseEntity<ResponseStructure<FoodOrder>>(structure, HttpStatus.OK);
        } else {
            throw new IdNotFoundException();
        }
    }
    
    
}
