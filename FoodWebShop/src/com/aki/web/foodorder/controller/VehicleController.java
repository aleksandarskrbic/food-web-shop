package com.aki.web.foodorder.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.aki.web.foodorder.config.Constants;
import com.aki.web.foodorder.domain.User;
import com.aki.web.foodorder.domain.Vehicle;
import com.aki.web.foodorder.domain.enums.Role;
import com.aki.web.foodorder.service.UserService;

@Path("vehicles")
public class VehicleController {

	@Context
	private HttpServletRequest request;

	@Context
	private ServletContext context;

	private final UserService userService = new UserService();

	@POST
	@Path("/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String add(@PathParam("username") String username, Vehicle vehicle) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);

		if (user != null) {

			if (user.getRole() == Role.ADMIN) {
				userService.addVehicle(username, vehicle);
				return "Vehicle added";
			}

			return "You don't have permission.";
		}

		return "Log in first.";
	}

	@DELETE
	@Path("/{vehicleId}/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String delete(@PathParam("vehicleId") String vehicleId, @PathParam("username") String username) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);

		if (user != null) {

			if (user.getRole() == Role.ADMIN) {
				boolean deletedFromDeliverer = userService.removeVehicle(vehicleId, username);

				if (deletedFromDeliverer) {
					return "Vehicle removed.";
				}

				return "Vehicle doesn't exist.";
			}

			return "You don't have permission.";
		}

		return "Log in first.";
	}
	
	@PUT
	@Path("/{vehicleId}/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String update(@PathParam("vehicleId") String vehicleId, @PathParam("username") String username, Vehicle vehicle) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);

		if (user != null) {

			if (user.getRole() == Role.ADMIN) {
				userService.updateVehicle(vehicleId, username, vehicle);
				return "Vehicle updated.";
			}

			return "You don't have permission.";
		}
		return "Log in first.";
	}

}
