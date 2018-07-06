package com.aki.web.foodorder.service;

import java.util.List;
import java.util.stream.Collectors;

import com.aki.web.foodorder.domain.Article;
import com.aki.web.foodorder.domain.Restaurant;
import com.aki.web.foodorder.domain.enums.Category;
import com.aki.web.foodorder.repository.RestaurantRepository;

public class RestaurantService {

	private final RestaurantRepository restaurantRepository;

	public RestaurantService() {
		restaurantRepository = new RestaurantRepository();
	}

	public List<Restaurant> getAllRestaurants() {
		List<Restaurant> restaurants = restaurantRepository.getAllRestaurants();

		if (restaurants != null)
			return restaurants;

		return null;
	}

	public Restaurant getRestaurantById(String id) {
		return restaurantRepository.getAllRestaurants()
								   .stream()
								   .filter(r -> r.getId().equals(id)).findFirst()
								   .orElse(null);
	}

	public void addRestaurant(Restaurant restaurant) {
		List<Restaurant> restaurants = restaurantRepository.getAllRestaurants();
		restaurants.add(restaurant);
		restaurantRepository.saveRestaurants(restaurants);
	}

	public boolean removeRestaurant(String id) {
		List<Restaurant> restaurants = restaurantRepository.getAllRestaurants();

		for (Restaurant r : restaurants) {
			if (r.getId().equals(id)) {
				r.setDeleted(true);
				restaurantRepository.saveRestaurants(restaurants);
				return true;
			}
		}

		return false;
	}

	public void updateRestaurant(Restaurant restaurant, String id) {
		List<Restaurant> restaurants = restaurantRepository.getAllRestaurants();

		for (Restaurant r : restaurants) {
			if (r.getId().equals(id)) {
				if (!restaurant.getName().isEmpty() || !restaurant.getName().trim().isEmpty()) {
					r.setName(restaurant.getName());
				}
				
				if (!restaurant.getAddress().isEmpty() || !restaurant.getAddress().trim().isEmpty()) {
					r.setAddress(restaurant.getAddress());
				}
				
				r.setCategory(restaurant.getCategory());
				
				restaurantRepository.saveRestaurants(restaurants);
				return;
			}
		}
	}

	public void addArticle(String restaurantId, Article article) {
		List<Restaurant> restaurants = restaurantRepository.getAllRestaurants();

		for (Restaurant r : restaurants) {
			if (r.getId().equals(restaurantId)) {
				List<Article> articles = r.getMenu();
				articles.add(article);
				r.setMenu(articles);
				restaurantRepository.saveRestaurants(restaurants);
				return;
			}
		}

	}

	public boolean removeArticle(String restaurantId, String articleId) {
		List<Restaurant> restaurants = restaurantRepository.getAllRestaurants();

		for (Restaurant r : restaurants) {
			if (r.getId().equals(restaurantId)) {
				List<Article> articles = r.getMenu();
				for (Article a : articles) {
					if (a.getId().equals(articleId)) {
						a.setDeleted(true);
						restaurantRepository.saveRestaurants(restaurants);
						return true;
					}
				}
			}
		}
		return false;
	}

	public void updateArticle(String articleId, String restaurantId, Article article) {
		List<Restaurant> restaurants = restaurantRepository.getAllRestaurants();

		for (Restaurant r : restaurants) {
			if (r.getId().equals(restaurantId)) {
				List<Article> articles = r.getMenu();
				for (Article a : articles) {
					if (a.getId().equals(articleId)) {
						a.setName(article.getName());
						a.setPrice(article.getPrice());
						a.setType(article.getType());
						a.setAmount(article.getAmount());
						a.setDescription(article.getDescription());
					}
				}
				r.setMenu(articles);
				restaurantRepository.saveRestaurants(restaurants);
				return;
			}
		}
	}

	public List<Restaurant> getRestaurantByCategory(String category) {
		List<Restaurant> restaurants = restaurantRepository.getAllRestaurants();
		
		if (category.equals("domestic")) {
			return restaurants.stream()
							  .filter(r -> r.getCategory() == Category.DOMESTIC)
							  .collect(Collectors.toList());
		} else if (category.equals("barbecue")) {
			return restaurants.stream()
					  		  .filter(r -> r.getCategory() == Category.BARBECUE)
					  		  .collect(Collectors.toList());
		} else if (category.equals("chinese")) {
			return restaurants.stream()
			  		  		  .filter(r -> r.getCategory() == Category.CHINESE)
			  		  		  .collect(Collectors.toList());
		} else if (category.equals("indian")) {
			return restaurants.stream()
			  		  		  .filter(r -> r.getCategory() == Category.INDIAN)
			  		  		  .collect(Collectors.toList());
		} else if (category.equals("pastry")) {
			return restaurants.stream()
			  		  		  .filter(r -> r.getCategory() == Category.PASTRY)
			  		  		  .collect(Collectors.toList());
		} else if (category.equals("pizza")) {
			return restaurants.stream()
			  		  		  .filter(r -> r.getCategory() == Category.PIZZERIA)
			  		  		  .collect(Collectors.toList());
		} else {
			return restaurants;
		}
	}

	public Article getArticleById(String restaurantId, String articleId) {
		List<Restaurant> restaurants = restaurantRepository.getAllRestaurants();
		
		for (Restaurant r : restaurants) {
			if (r.getId().equals(restaurantId)) {
				List<Article> articles = r.getMenu();
				for (Article a : articles) {
					if (a.getId().equals(articleId)) {
						return a;
					}
				}
			}
		}
		
		return null;
	}


}
