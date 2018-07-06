package com.aki.web.foodorder.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.aki.web.foodorder.domain.Order;
import com.aki.web.foodorder.domain.User;
import com.aki.web.foodorder.domain.Vehicle;
import com.aki.web.foodorder.domain.enums.Role;
import com.aki.web.foodorder.repository.UserRepository;

public class UserService {

	private final UserRepository userRepository;

	public UserService() {
		userRepository = new UserRepository();
	}

	public boolean addUser(User user) {
		List<User> users = userRepository.getAllUsers();

		user.setUsername(user.getUsername().toLowerCase());
		boolean checkExistence = users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()));

		if (!checkExistence) {
			users.add(user);
			userRepository.saveUsers(users);
			return true;
		}

		return false;
	}

	public List<User> getAllUsers() {
		List<User> users = userRepository.getAllUsers();

		if (users != null)
			return users;

		return null;
	}

	public User findUserByUsername(String username) {
		return userRepository.getAllUsers().stream().filter(u -> u.getUsername().equals(username)).findFirst()
				.orElse(null);
	}

	public void changeUserRole(String username, Role role) {
		List<User> users = userRepository.getAllUsers();

		for (User user : users) {
			if (user.getUsername().equals(username)) {
				user.setRole(role);
				userRepository.saveUsers(users);
				return;
			}
		}
	}

	public boolean updateUser(User user) {
		List<User> users = userRepository.getAllUsers();
		boolean checkExistence;

		if (users != null) {
			checkExistence = users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()));

			if (checkExistence) {
				for (int i = 0; i < users.size(); ++i) {
					if (users.get(i).getUsername().equals(user.getUsername())) {
						users.get(i).setEmail(user.getEmail());
						users.get(i).setFirstName(user.getFirstName());
						users.get(i).setLastName(user.getLastName());
						users.get(i).setPassword(user.getPassword());
						userRepository.saveUsers(users);
						break;
					}
				}
				return true;
			}
		}

		return false;
	}

	public boolean removeUser(String username) {
		List<User> users = userRepository.getAllUsers();
		User user = findUserByUsername(username);

		if (users != null && user != null) {
			users.remove(user);
			userRepository.saveUsers(users);
			return true;
		}

		return false;
	}

	public void addVehicle(String username, Vehicle vehicle) {
		List<User> users = userRepository.getAllUsers();

		for (User u : users) {
			if (u.getUsername().equals(username) && u.getRole() == Role.DELIVERER) {
				u.setVehicle(vehicle);
				userRepository.saveUsers(users);
				return;
			}
		}
	}

	public boolean removeVehicle(String vehicleId, String username) {
		List<User> users = userRepository.getAllUsers();

		for (User u : users) {
			if (u.getUsername().equals(username) && u.getRole() == Role.DELIVERER) {
				Vehicle v = u.getVehicle();
				v.setDeleted(true);
				u.setVehicle(v);
				userRepository.saveUsers(users);
				return true;
			}
		}
		return false;
	}

	public void updateVehicle(String vehicleId, String username, Vehicle vehicle) {
		List<User> users = userRepository.getAllUsers();

		for (User u : users) {
			if (u.getUsername().equals(username) && u.getRole() == Role.DELIVERER) {
				u.setVehicle(vehicle);
				userRepository.saveUsers(users);
				return;
			}
		}
	}

	public void addOrder(String username, String id) {
		List<User> users = userRepository.getAllUsers();

		for (User u : users) {
			if (u.getUsername().equals(username)) {
					List<String> orders = u.getOrdersCustomer();
					if (orders == null)
						orders = new ArrayList<>();
					
					orders.add(id);
					u.setOrdersCustomer(orders);
					userRepository.saveUsers(users);
					return;
			
			}
		}
	}

	public void addToFavourites(String username, String restaurantId) {
		List<User> users = userRepository.getAllUsers();

		for (User u : users) {
			if (u.getUsername().equals(username)) {
				List<String> favs = u.getRestaurants();
				if (favs == null)
					favs = new ArrayList<>();
				
				for (String s : favs) {
					if (s.equals(restaurantId)) {
						return;
					}
				}
				
				favs.add(restaurantId);
				u.setRestaurants(favs);
				userRepository.saveUsers(users);
				return;
			}
		}
	}

	public void removeFromFavourites(String username, String restaurantId) {
		List<User> users = userRepository.getAllUsers();

		for (User u : users) {
			if (u.getUsername().equals(username)) {
				List<String> favs = u.getRestaurants();
				if (favs != null) {
					favs.remove(restaurantId);
					u.setRestaurants(favs);
					userRepository.saveUsers(users);
				}
				
				return;
			}
		}
		
	}

	public void addDeliverer(String id, String username) {
		List<User> users = userRepository.getAllUsers();

		for (User u : users) {
			if (u.getUsername().equals(username)) {
				List<String> orders = u.getOrdersDelivery();
				if (orders != null) {
					orders.add(id);
					u.setOrdersDelivery(orders);
					userRepository.saveUsers(users);
					return;
				}
				
				orders = new ArrayList<>();
				orders.add(id);
				u.setOrdersDelivery(orders);
				userRepository.saveUsers(users);
				return;
			}
		}
	}

	public List<String> getOrders(String username) {
		User user = findUserByUsername(username);
		return user.getOrdersDelivery();
	}

	public void finishOrder(Order order, String username) {
		List<User> users = userRepository.getAllUsers();
		
		for (User u : users) {
			if (u.getUsername().equals(username)) {
				u.setOrdersDelivery(new ArrayList<>());
				userRepository.saveUsers(users);
			}
		}
		
	}

	public void updateBonusPoints(int bonusPoints, String username) {
		List<User> users = userRepository.getAllUsers();
		
		for (User u : users) {
			if (u.getUsername().equals(username)) {
				int bp = u.getBonusPoints();
				bp += bonusPoints;
				
				if (bp > 10) {
					bp = 10;
				}
				
				if (bp < 0) {
					bp = 0;
				}
				u.setBonusPoints(bp);
				userRepository.saveUsers(users);
				return;
			}
		}
		
	}

	public List<String> getFreeDeliveres(List<String> busyDeliverers) {
		List<User> deliverers = userRepository.getAllUsers().stream()
															.filter(u -> u.getRole() == Role.DELIVERER)
															.collect(Collectors.toList());
		
		List<String> freeDeliverers = deliverers.stream()
											    .map(User::getUsername)
											    .collect(Collectors.toList());
		
		freeDeliverers.removeAll(busyDeliverers);
		
		return freeDeliverers;
	}
}
