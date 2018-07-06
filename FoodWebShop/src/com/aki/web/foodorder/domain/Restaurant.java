package com.aki.web.foodorder.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.aki.web.foodorder.domain.enums.Category;
import com.aki.web.foodorder.domain.enums.Type;

public class Restaurant {
	
	private String id;
	
	private String name;
	
	private String address;
	
	private Category category;
	
	private List<Article> menu;
	
	private boolean deleted;
	
	public Restaurant() {
		id = UUID.randomUUID().toString();
		this.menu = new ArrayList<>();
		this.deleted = false;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<Article> getBevrages() {
		return menu.stream()
				   .filter(item -> item.getType() == Type.BEVERAGE)
				   .collect(Collectors.toList());
	}
	
	public List<Article> getFood() {
		return menu.stream()
				   .filter(item -> item.getType() == Type.FOOD)
				   .collect(Collectors.toList());
	}
	
	public List<Article> getMenu() {
		return menu.stream()
				   .filter(item -> item.isDeleted() == false)
				   .collect(Collectors.toList());
	}

	public void setMenu(List<Article> menu) {
		this.menu = menu;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
