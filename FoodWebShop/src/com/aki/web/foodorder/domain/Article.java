package com.aki.web.foodorder.domain;

import java.util.UUID;

import com.aki.web.foodorder.domain.enums.Type;

public class Article {
	
	private String id;
	
	private Type type;

	private String name;
	
	private double price;
	
	private String description;
	
	private int amount;
	
	private boolean deleted;
	
	public Article() {
		id = UUID.randomUUID().toString();
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public String getId() {
		return id;
	}

}
