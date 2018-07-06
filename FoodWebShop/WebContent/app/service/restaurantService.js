app.factory('restaurantService', function ($http) {

	var service = {};

	service.getRestaurants = function () {
		return $http.get('/FoodWebShop/rest/restaurants?category=');
	};

	service.getRestaurantsByCategory = function (category) {
		return $http.get('/FoodWebShop/rest/restaurants?category=' + category);
	};

	service.deleteRestaurant = function (id) {
		return $http.delete('/FoodWebShop/rest/restaurants/' + id);
	};

	service.addRestaurant = function (restaurant) {
		return $http.post('/FoodWebShop/rest/restaurants', restaurant);
	};

	service.updateRestaurant = function (restaurant, id) {
		return $http.put('/FoodWebShop/rest/restaurants/' + id, restaurant);
	};

	service.getRestaurantById = function (id) {
		return $http.get('/FoodWebShop/rest/restaurants/' + id);
	};

	service.addArticle = function (article, restaurantId) {
		return $http.post('/FoodWebShop/rest/articles/' + restaurantId, article);
	};

	service.deleteArticle = function (articleId, restaurantId) {
		return $http.delete('/FoodWebShop/rest/articles/' + restaurantId + '/' + articleId);
	};

	service.updateArticle = function (articleId, restaurantId, article) {
		return $http.put('/FoodWebShop/rest/articles/' + restaurantId + '/' + articleId, article);
	};

	service.addVehicle = function (vehicle, username) {
		return $http.post('/FoodWebShop/rest/vehicles/' + username, vehicle);
	};

	service.deleteVehicle = function (vehicleId, username) {
		return $http.delete('/FoodWebShop/rest/vehicles/' + vehicleId + '/' + username);
	};

	service.updateVehicle = function (vehicleId, username, vehicle) {
		return $http.put('/FoodWebShop/rest/vehicles/' + vehicleId + '/' + username, vehicle);
	};

	service.getArticleById = function (restaurantId, articleId) {
		return $http.get('/FoodWebShop/rest/articles/' + restaurantId + '/' + articleId);
	};

	service.getPopularFood = function () {
		return $http.get('/FoodWebShop/rest/articles/food');
	}

	service.getPopularDrinks = function () {
		return $http.get('/FoodWebShop/rest/articles/drinks');
	}

	return service;
});