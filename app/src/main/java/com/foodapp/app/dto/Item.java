package com.foodapp.app.dto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="item")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private boolean availability;
	private String name;
	private String description;
	private long price;
	private long quantity;
	@JsonProperty("id")
	private void unpackNested(Integer id) {
	    this.id=id;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="foodOrder_id")
	@JsonIgnore
	private FoodOrder foodOrder;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="menu_id")
	@JsonIgnore
	private Menu menu;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public FoodOrder getFoodOrder() {
		return foodOrder;
	}

	public void setFoodOrder(FoodOrder foodOrder) {
		this.foodOrder = foodOrder;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", availability=" + availability + ", name=" + name + ", description=" + description
				+ ", price=" + price + ", quantity=" + quantity + ", foodOrder=" + foodOrder + ", menu=" + menu + "]";
	}
	
	
	
	

}
