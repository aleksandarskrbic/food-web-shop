package com.aki.web.foodorder.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.aki.web.foodorder.config.Constants;
import com.aki.web.foodorder.domain.User;
import com.aki.web.foodorder.domain.enums.Role;
import com.aki.web.foodorder.service.OrderService;
import com.aki.web.foodorder.service.UserService;

@Path("users")
public class UserController {

	@Context
	private HttpServletRequest request;

	@Context
	private ServletContext context;

	private final UserService userService = new UserService();
	
	private final OrderService orderService = new OrderService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GET
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getCustomers() {
		return userService.getAllUsers().stream()
										.filter(u -> u.getRole() == Role.CUSTOMER)
										.map(User::getUsername)
										.collect(Collectors.toList());
	}
	
	@GET
	@Path("/deliverers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getDeliverers() {
		if (orderService.getBusyDeliverers().isEmpty()) {
			return userService.getAllUsers().stream()
											.filter(u -> u.getRole() == Role.DELIVERER)
											.map(User::getUsername)
											.collect(Collectors.toList());
		}
		
		return userService.getFreeDeliveres(orderService.getBusyDeliverers());
	}
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("username") String username) {
		return userService.findUserByUsername(username);
	}

	@POST
	@Path("/register")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String registerUser(User user) {

		if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null
				|| user.getUsername().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
			return "Username, password and email are required.";
		} else if (userService.findUserByUsername(user.getUsername()) != null) {
			return "Username already exists.";
		} else {
			userService.addUser(user);
			return "Succesfully registered.";
		}
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User loginUser(User user) {

		HttpSession session = request.getSession();

		if (user.getUsername() == null || user.getPassword() == null || user.getUsername().isEmpty()
				|| user.getPassword().isEmpty()) {
			//return "Fill username and password to log in!";
			return null;
		} else if (userService.findUserByUsername(user.getUsername()) != null) {
			User loggedUser = userService.findUserByUsername(user.getUsername());

			if (loggedUser.getPassword().equals(user.getPassword()) == true) {
				session.setAttribute(Constants.LOGGED_IN, loggedUser);
				return loggedUser;
				//return "Successfully logged in!";
			} else {
				//return "Login failed!";
				return null;
			}
		} else if (session.getAttribute(Constants.LOGGED_IN) != null) {
			//return "Already logged in.";
			return (User) session.getAttribute(Constants.LOGGED_IN);
		} else {
			//return "Can't log in!";
			return null;
		}
	}

	@GET
	@Path("/logout")
	@Produces(MediaType.TEXT_PLAIN)
	public String logoutUser() {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);

		if (user != null) {
			session.invalidate();
			return "Logged out!";
		}
		
		return "Already logged out!";
	}

	@GET
	@Path("/me")
	@Produces(MediaType.APPLICATION_JSON)
	public User getCurrentUser() {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);

		if (user != null) {
			return user;
		}

		return null;
	}

	@GET
	@Path("/change/{role}/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String changeRole(@PathParam("role") String role, @PathParam("username") String username) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		if (user != null) {
			
			if (user.getRole() == Role.ADMIN) {
				
				if (role.equals("admin")) {
					userService.changeUserRole(username, Role.ADMIN);
					return "Successfully updated!";
				} else if (role.equals("customer")) {
					userService.changeUserRole(username, Role.CUSTOMER);
					return "Successfully updated!";
				} else if (role.equals("deliverer")) {
					userService.changeUserRole(username, Role.DELIVERER);
					return "Successfully updated!";
				} else {
					return "Invalid Role!";
				}
			} else {
				return "You don't have permission to change user role.";
			}
		} else {
			return "Log in first!";
		}
	}
	
	@POST
	@Path("/fav/{username}/{restaurantId}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addToFavourites(@PathParam("username") String username, @PathParam("restaurantId") String restaurantId) {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		if (user != null) {
			userService.addToFavourites(username, restaurantId);
			return "Restaurant added to favourites!";
		}
		
		return "Log in first!";
	}
	
	@DELETE
	@Path("/fav/{username}/{restaurantId}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String removeFromFavourites(@PathParam("username") String username, @PathParam("restaurantId") String restaurantId) {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		if (user != null) {
			userService.removeFromFavourites(username, restaurantId);
			return "Restaurant removed from favourites!";
		}
		
		return "Log in first!";
	}
}
