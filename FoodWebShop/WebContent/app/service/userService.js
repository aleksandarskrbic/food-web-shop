app.factory('userService', function ($http) {

	var service = {};

	service.getUsers = function () {
		return $http.get('/FoodWebShop/rest/users');
	};

	service.getCustomers = function () {
		return $http.get('/FoodWebShop/rest/users/customers');
	};
	service.getDeliverers = function () {
		return $http.get('/FoodWebShop/rest/users/deliverers');
	};

	service.getUser = function (username) {
		return $http.get('/FoodWebShop/rest/users/' + username);
	};

	service.addUser = function (user) {
		return $http.post('/FoodWebShop/rest/users/register', user);
	};

	service.login = function (userToLogin) {
		return $http.post('/FoodWebShop/rest/users/login', userToLogin);
	};

	service.logout = function () {
		return $http.get('/FoodWebShop/rest/users/logout');
	};

	service.getCurrentUser = function () {
		return $http.get('/FoodWebShop/rest/users/me');
	};

	service.changeRole = function (role, username) {
		return $http.get('/FoodWebShop/rest/users/change/' + role + "/" + username);
	};

	service.addToFavourites = function (username, restaurantId) {
		return $http.post('/FoodWebShop/rest/users/fav/' + username + '/' + restaurantId);
	}

	service.removeFromFavourites = function (username, restaurantId) {
		return $http.delete('/FoodWebShop/rest/users/fav/' + username + '/' + restaurantId);
	}

	return service;
});