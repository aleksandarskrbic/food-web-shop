package com.aki.web.foodorder.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aki.web.foodorder.config.Constants;
import com.aki.web.foodorder.domain.Restaurant;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class RestaurantRepository {

	private ObjectMapper objectMapper;
	private File file;
	private List<Restaurant> restaurants;

	public RestaurantRepository() {
		objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		file = new File(Constants.RESTAURANT_REPOSITORY);

		if (file.exists()) {
			restaurants = getRestaurants();
		} else {
			restaurants = new ArrayList<>();
		}
	}

	private synchronized List<Restaurant> getRestaurants() {

		try {
			return objectMapper.readValue(file,
					objectMapper.getTypeFactory().constructCollectionType(List.class, Restaurant.class));
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

	public synchronized void saveRestaurants(List<Restaurant> restaurants) {

		try {
			objectMapper.writeValue(file, restaurants);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Restaurant> getAllRestaurants() {
		return restaurants;
	}
}
