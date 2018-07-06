package com.aki.web.foodorder.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.aki.web.foodorder.domain.enums.Status;

public class Order {
	
	private String id;

	private List<Item> items;
	
	private String dateTime;
	
	private String restaurantId;
	
	private String userId;
	
	private String deliveryPersonId;
	
	private Status status;
	
	private String note;
	
	private double bill;
	
	private int bonusPoints;
	
	public Order() {
		id = UUID.randomUUID().toString();
		this.dateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		this.status = Status.ORDERED;
	}
	
	public int getBonusPoints() {
		return bonusPoints;
	}

	public void setBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	public double getBill() {
		return bill;
	}


	public void setBill(double bill) {
		this.bill = bill;
	}


	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item>  items) {
		this.items = items;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeliveryPersonId() {
		return deliveryPersonId;
	}

	public void setDeliveryPersonId(String deliveryPersonId) {
		this.deliveryPersonId = deliveryPersonId;
	}
	
	
	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public String getId() {
		return id;
	}

}
