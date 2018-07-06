package com.aki.web.foodorder.controller;

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
import com.aki.web.foodorder.domain.Article;
import com.aki.web.foodorder.domain.User;
import com.aki.web.foodorder.domain.enums.Role;
import com.aki.web.foodorder.service.OrderService;
import com.aki.web.foodorder.service.RestaurantService;

@Path("articles")
public class ArticleController {

	@Context
	private HttpServletRequest request;

	@Context
	private ServletContext context;

	private final RestaurantService restaurantService = new RestaurantService();
	
	private final OrderService orderService = new OrderService();
	
	
	@GET
	@Path("/{restaurantId}/{articleId}")
	public Article getArticleById(@PathParam("restaurantId") String restaurantId, @PathParam("articleId") String articleId) {
		return restaurantService.getArticleById(restaurantId, articleId);
	}

	@POST
	@Path("/{restaurantId}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String add(@PathParam("restaurantId") String restaurantId, Article article) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);

		if (user != null) {

			if (user.getRole() == Role.ADMIN) {

				if (article.getName() != null && !article.getName().trim().isEmpty()) {
					restaurantService.addArticle(restaurantId, article);
					return "Article added.";
				}

				return "Unable to add article.";
			}

			return "You don't have permission.";
		}

		return "Log in first.";
	}

	@DELETE
	@Path("/{restaurantId}/{articleId}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String delete(@PathParam("articleId") String articleId, @PathParam("restaurantId") String restaurantId) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);

		if (user != null) {

			if (user.getRole() == Role.ADMIN) {
				boolean deletedFromRestaurant = restaurantService.removeArticle(restaurantId, articleId);

				if (deletedFromRestaurant) {
					return "Article removed.";
				}

				return "Article doesn't exist.";
			}

			return "You don't have permission.";
		}

		return "Log in first.";
	}

	@PUT
	@Path("/{restaurantId}/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String update(@PathParam("id") String articleId, @PathParam("restaurantId") String restaurantId,
			Article article) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGGED_IN);

		if (user != null) {

			if (user.getRole() == Role.ADMIN) {

				if (article.getName() != null && !article.getName().trim().isEmpty()) {
					restaurantService.updateArticle(articleId, restaurantId, article);
					return "Article updated.";
				}

				return "Unable to update article.";
			}

			return "You don't have permission.";
		}
		return "Log in first.";
	}
	
	
	@GET
	@Path("/drinks")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> popularDrinks() {
		return orderService.getPopularDrinks();
	}
	
	@GET
	@Path("/food")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> popularFood() {
		return orderService.getPopularFood();
	}

}