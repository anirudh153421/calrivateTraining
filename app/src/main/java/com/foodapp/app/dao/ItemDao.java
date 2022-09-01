package com.foodapp.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodapp.app.dto.Item;
import com.foodapp.app.repository.ItemRepository;

@Repository
public class ItemDao {
	
	@Autowired
	ItemRepository itemRepository;
	
	public Item saveItem(Item item) {
		return itemRepository.save(item);
	}
	
	public Optional<Item> findItemById(int id){
		return itemRepository.findById(id);
	}
	
	public List<Item> findAllItems(){
		return itemRepository.findAll();
	}
	
	public Item updateItem(Item item,int id) {
		Optional<Item> optional=findItemById(id);
		Item item2=optional.get();
		item2.setAvailability(item.isAvailability());
		item2.setDescription(item.getDescription());
		item2.setName(item.getName());
		item2.setPrice(item.getPrice());
		item2.setQuantity(item.getQuantity());
		itemRepository.save(item2);
		return item2;
	}
	
	public Optional<Item> deleteItem(int id){
		Optional<Item> optional=findItemById(id);
		if(optional.isEmpty()) {
			return null;
		}
		else
		{
			itemRepository.delete(optional.get());
			return optional;
		}
	}
	public long getItemCount() {
		return itemRepository.count();
	}
}
