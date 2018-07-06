package com.aki.web.foodorder.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aki.web.foodorder.domain.enums.Role;

public class User {

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private Role role;

	private String phone;

	private String email;

	private String registrationDate;

	private List<String> ordersCustomer;

	private List<String> restaurants;

	private Vehicle vehicle;

	private List<String> ordersDelivery;
	
	private int bonusPoints;

	public User() {
		this.registrationDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date()).toString();
		this.role = Role.CUSTOMER;
		
		this.ordersCustomer = new ArrayList<String>();
		this.restaurants = new ArrayList<String>();
		this.ordersDelivery = new ArrayList<String>();
		this.bonusPoints = 0;
	}
	

	public int getBonusPoints() {
		return bonusPoints;
	}

	public void setBonusPoints(int bonusPoints) {
		if (bonusPoints > 10) {
			this.bonusPoints = 10;
		} else {
			this.bonusPoints = bonusPoints;	
		}
	}


	public List<String> getOrdersCustomer() {
		if (this.role == Role.CUSTOMER) {
			return ordersCustomer;
		}

		return null;
	}

	public void setOrdersCustomer(List<String> ordersCustomer) {
		if (this.role == Role.CUSTOMER) {
			this.ordersCustomer = ordersCustomer;
		}
	}

	public List<String> getRestaurants() {
		if (this.role == Role.CUSTOMER) {
			return restaurants;
		}

		return null;
	}

	public void setRestaurants(List<String> restaurants) {
		if (this.role == Role.CUSTOMER) {
			this.restaurants = restaurants;
		}
	}

	public Vehicle getVehicle() {
		if (this.role == Role.DELIVERER) {
			return vehicle;
		}

		return null;
	}

	public void setVehicle(Vehicle vehicle) {
		if (this.role == Role.DELIVERER) {
			this.vehicle = vehicle;
		}
	}

	public List<String> getOrdersDelivery() {
		if (this.role == Role.DELIVERER) {
			return ordersDelivery;
		}
		return null;
	}

	public void setOrdersDelivery(List<String> ordersDelivery) {
		if (this.role == Role.DELIVERER) {
			this.ordersDelivery = ordersDelivery;
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

}
