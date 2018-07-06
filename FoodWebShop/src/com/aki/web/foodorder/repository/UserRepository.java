package com.aki.web.foodorder.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.aki.web.foodorder.config.Constants;
import com.aki.web.foodorder.domain.User;

public class UserRepository {

	private ObjectMapper objectMapper;
	private File file;
	private List<User> users;

	public UserRepository() {
		objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		file = new File(Constants.USER_REPOSITORY);

		if (file.exists()) {
			users = getUsers();
		} else {
			users = new ArrayList<>();
		}
	}

	private synchronized List<User> getUsers() {

		try {
			return objectMapper.readValue(file,
					objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
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

	public synchronized void saveUsers(List<User> users) {

		try {
			objectMapper.writeValue(file, users);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<User> getAllUsers() {
		return users;
	}
	
}
