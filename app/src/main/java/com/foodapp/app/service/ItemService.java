package com.foodapp.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foodapp.app.dao.ItemDao;
import com.foodapp.app.dto.Item;
import com.foodapp.app.exception.DataBaseEmptyException;
import com.foodapp.app.exception.IdNotFoundException;
import com.foodapp.app.util.ResponseStructure;

@Service
public class ItemService {
	
	@Autowired
    ItemDao dao;

    public ResponseEntity<ResponseStructure<Item>> saveItem(Item item) {
        ResponseStructure<Item> structure = new ResponseStructure<>();
        structure.setMessage("Item Saved Successfully");
        structure.setStatus(HttpStatus.CREATED.value());
        structure.setData(dao.saveItem(item));
        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<Item>> deleteItem(int id) {
    	if(dao.getItemCount()==0)
    		throw new DataBaseEmptyException();
        Optional<Item> optional = dao.deleteItem(id);
        ResponseStructure<Item> structure = new ResponseStructure<>();
        if (optional == null) {
            throw new IdNotFoundException();
        } else {
            structure.setMessage("deleted Successfully");
            structure.setStatus(HttpStatus.OK.value());
            structure.setData(optional.get());
            return new ResponseEntity<ResponseStructure<Item>>(structure, HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseStructure<Item>> getItemById(int id) {
    	if(dao.getItemCount()==0)
    		throw new DataBaseEmptyException();
        Optional<Item> optional = dao.findItemById(id);
        ResponseStructure<Item> structure = new ResponseStructure<>();
        if (optional.isEmpty()) {
            throw new IdNotFoundException();
        } else {
            structure.setMessage("Item Found");
            structure.setStatus(HttpStatus.OK.value());
            structure.setData(optional.get());
            return new ResponseEntity<ResponseStructure<Item>>(structure, HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseStructure<List<Item>>> getAllItems() {
    	if(dao.getItemCount()==0)
    		throw new DataBaseEmptyException();
        ResponseStructure<List<Item>> structure = new ResponseStructure<>();
        List<Item> list = dao.findAllItems();
        if (list.isEmpty())
            throw new IdNotFoundException();
        else {
            structure.setMessage("fetched Successfully");
            structure.setStatus(HttpStatus.OK.value());
            structure.setData(list);
            return new ResponseEntity<ResponseStructure<List<Item>>>(structure, HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseStructure<Item>> updateItem(Item item, int id) {
    	if(dao.getItemCount()==0)
    		throw new DataBaseEmptyException();
        Item item2 = dao.updateItem(item, id);
        ResponseStructure<Item> structure = new ResponseStructure<>();
        if (item2 != null) {
            structure.setMessage("Updated Successfully");
            structure.setStatus(HttpStatus.OK.value());
            structure.setData(item);
            return new ResponseEntity<ResponseStructure<Item>>(structure, HttpStatus.OK);
        } else {
            throw new IdNotFoundException();
        }
    }
}
