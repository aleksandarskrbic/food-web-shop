package com.aki.web.foodorder.controller;

import java.util.ArrayList;
import java.util.List;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.aki.web.foodorder.config.Constants;
import com.aki.web.foodorder.domain.Item;
import com.aki.web.foodorder.domain.Order;
import com.aki.web.foodorder.domain.User;
import com.aki.web.foodorder.service.OrderService;
import com.aki.web.foodorder.service.UserService;

@Path("orders")
public class OrderController {

	@Context
	private HttpServletRequest request;

	@Context
	private ServletContext context;

	private final OrderService orderService = new OrderService();
	
	private final UserService userService = new UserService();
	
	@POST
	@Path("/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addOrder(@PathParam("username") String username, Order order) {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		if (user != null) {
			orderService.addOrder(order);
			userService.addOrder(username, order.getId());
			return "Order added successfully!";
		}
		
		return "Log in to order!";
	}
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Order> getOrders(@PathParam("username") String username) {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		if (user != null) {
			return orderService.getUserOrders(username);
		}
		
		return null;
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Order> getAll() {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		if (user != null) {
			return orderService.getAllOrders();
		}
		
		return null;
	}
	
	@GET
	@Path("/current/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Order getCurrnetOrder(@PathParam("username") String username) {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		if (user != null) {
			return orderService.getCurrentOrder(username);
		}
		
		return null;
	}
	
	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Order getOrder(@PathParam("id") String id) {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		if (user != null) {
			return orderService.getOrderById(id);
		}
		
		return null;
	}
	
	@GET
	@Path("/prev/{role}/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Order> getPrevOrders(@PathParam("role") String role, @PathParam("username") String username) {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		if (user != null) {
			return orderService.getPrevOrders(username, role);
		}
		
		return null;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Order> getPendingOrders() {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		if (user != null) {
			return orderService.getPendingOrders();
		}
		
		return null;
	}
	
	
	
	@PUT
	@Path("/{id}/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String takeOrder(@PathParam("id") String id, @PathParam("username") String username) {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		if (user != null) {
			orderService.addDeliverer(id, username);
			userService.addDeliverer(id, username);
			return "Deliverer added to order.";
		}
		
		return "Can't add deliverer.";
	}
	
	@PUT
	@Path("/done/{username}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String finishOrder(@PathParam("username") String username, @PathParam("id") String id, Order order) {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		if (user != null) {
			orderService.finishOrder(id);
			userService.finishOrder(order, username);
			userService.updateBonusPoints(order.getBonusPoints(), order.getUserId());
			return "Order delivered.";
		}
		
		return "Can't deliver order.";
	}
	
	@POST
	@Path("/item")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addItem(Item item) {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		@SuppressWarnings("unchecked")
		List<Item> items = (List<Item>) session.getAttribute(Constants.ITEMS);
		
		
		if (user != null && items != null) {
			items.add(item);
			session.setAttribute(Constants.ITEMS, items);
		} else {
			items = new ArrayList<>();
			items.add(item);
			session.setAttribute(Constants.ITEMS, items);
		}
		
	}
	
	@DELETE
	@Path("/item/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteItem(@PathParam("id") String id) {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		@SuppressWarnings("unchecked")
		List<Item> items = (List<Item>) session.getAttribute(Constants.ITEMS);
		
		
		if (user != null && items != null) {
			for (Item item : items) {
				if (item.getId().equals(id)) {
					items.remove(item);
					break;
				}
			}
			session.setAttribute(Constants.ITEMS, items);
		} 
	}
	
	@GET
	@Path("/items")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getItems() {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		@SuppressWarnings("unchecked")
		List<Item> items = (List<Item>) session.getAttribute(Constants.ITEMS);
		
		if (user != null && items != null) {
			return items;
		}
		
		return new ArrayList<>();
	}
	
	@PUT
	@Path("/items")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void emptyCart() {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);
		
		@SuppressWarnings("unchecked")
		List<Item> items = (List<Item>) session.getAttribute(Constants.ITEMS);
		
		if (user != null && items != null) {
			items = new ArrayList<>();
			session.setAttribute(Constants.ITEMS, items);
		}
		
	}
}
