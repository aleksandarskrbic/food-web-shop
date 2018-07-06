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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.aki.web.foodorder.config.Constants;
import com.aki.web.foodorder.domain.Restaurant;
import com.aki.web.foodorder.domain.User;
import com.aki.web.foodorder.domain.enums.Role;
import com.aki.web.foodorder.service.RestaurantService;

@Path("restaurants")
public class RestaurantController {

	@Context
	private HttpServletRequest request;

	@Context
	private ServletContext context;

	private final RestaurantService restaurantService = new RestaurantService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Restaurant> getAll(@QueryParam("category") String category) {
		return restaurantService.getRestaurantByCategory(category).stream()
																  .filter(r -> r.isDeleted() == false)
																  .collect(Collectors.toList());
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Restaurant getRestaurantById(@PathParam("id") String id) {
		return restaurantService.getRestaurantById(id);
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String add(Restaurant restaurant) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);

		if (user != null) {

			if (user.getRole() == Role.ADMIN) {

				if (restaurant.getName() != null && !restaurant.getName().trim().isEmpty()) {
					restaurantService.addRestaurant(restaurant);
					return "Restaurant added.";
				}

				return "Unable to add restaurant.";
			}

			return "You don't have permission.";
		}

		return "Log in first.";
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String delete(@PathParam("id") String id) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);

		if (user != null) {

			if (user.getRole() == Role.ADMIN) {
				boolean deleted = restaurantService.removeRestaurant(id);

				if (deleted) {
					return "Restaurant removed.";
				}

				return "Restaurant doesn't exist.";
			}

			return "You don't have permission.";
		}
		return "Log in first.";
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String update(Restaurant restaurant, @PathParam("id") String id) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);

		if (user != null) {

			if (user.getRole() == Role.ADMIN) {

				if (restaurant.getName() != null && !restaurant.getName().trim().isEmpty()) {
					restaurantService.updateRestaurant(restaurant, id);
					return "Restaurant updated.";
				}

				return "Unable to update restaurant.";
			}

			return "You don't have permission.";
		}
		return "Log in first.";
	}

}
