app.factory('orderService', function ($http) {

	var service = {};

	service.addOrder = function (username, order) {
		return $http.post('/FoodWebShop/rest/orders/' + username, order);
	};

	service.getUserOrders = function (username) {
		return $http.get('/FoodWebShop/rest/orders/' + username);
	};

	service.getAllOrders = function () {
		return $http.get('/FoodWebShop/rest/orders/all');
	};

	service.getPendingOrders = function () {
		return $http.get('/FoodWebShop/rest/orders');
	};

	service.takeOrder = function (id, username) {
		return $http.put('/FoodWebShop/rest/orders/' + id + '/' + username);
	};

	service.getCurrentOrder = function (username) {
		return $http.get('/FoodWebShop/rest/orders/current/' + username);
	};

	service.getPreviousOrders = function (role, username) {
		return $http.get('/FoodWebShop/rest/orders/prev/' + role + '/' + username);
	};

	service.getOrderById = function (id) {
		return $http.get('/FoodWebShop/rest/orders/get/' + id);
	};

	service.finishOrder = function (currentOrder, username, id) {
		return $http.put('/FoodWebShop/rest/orders/done/' + username + '/' + id, currentOrder);
	};

	service.getItems = function () {
		return $http.get('/FoodWebShop/rest/orders/items');
	};

	service.addItem = function (item) {
		return $http.post('/FoodWebShop/rest/orders/item', item);
	};

	service.removeItem = function (id) {
		return $http.delete('/FoodWebShop/rest/orders/item/' + id);
	};

	service.emptyCart = function() {
		return $http.put('/FoodWebShop/rest/orders/items');
	}

	return service;
});