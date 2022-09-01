package com.foodapp.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodapp.app.dto.Menu;
import com.foodapp.app.repository.MenuRepository;

@Repository
public class MenuDao {
	
	@Autowired
	MenuRepository menuRepository;
	
	public Menu saveMenu(Menu menu) {
		return menuRepository.save(menu);
	}
	
	public Optional<Menu> findMenuById(int id){
		return menuRepository.findById(id);
	}
	
	public List<Menu> findAllMenus(){
		return menuRepository.findAll();
	}
	
	public Menu updateMenu(Menu menu,int id) {
		Optional<Menu> optional=findMenuById(id);
		Menu menu2=optional.get();
		menuRepository.save(menu2);
		return menu2;
	}
	
	public Optional<Menu> deleteMenu(int id){
		Optional<Menu> optional=findMenuById(id);
		if(optional.isEmpty())
			return null;
		else
		{
			menuRepository.delete(optional.get());
			return optional;
		}
	}
	public long getMenuCount() {
		return menuRepository.count();
	}
}

