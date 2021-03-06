package com.aki.web.foodorder.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aki.web.foodorder.config.Constants;
import com.aki.web.foodorder.domain.Order;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class OrderRepository {

	private ObjectMapper objectMapper;
	private File file;
	private List<Order> orders;

	public OrderRepository() {
		objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		file = new File(Constants.ORDER_REPOSITORY);

		if (file.exists()) {
			orders = getOrders();
		} else {
			orders = new ArrayList<>();
		}
	}

	private synchronized List<Order> getOrders() {

		try {
			return objectMapper.readValue(file,
					objectMapper.getTypeFactory().constructCollectionType(List.class, Order.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public synchronized void saveOrders(List<Order> orders) {

		try {
			objectMapper.writeValue(file, orders);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Order> getAllOrders() {
		return orders;
	}
}
