var app = angular.module('app', ['ngRoute']);

app.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
	$locationProvider.hashPrefix('');
	$routeProvider
		.when('/', {
			controller: 'homeController',
			templateUrl: 'partials/home/home.html'
		})
		.when('/category/:category', {
			controller: 'homeController',
			templateUrl: 'partials/home/restaurants.html'
		})
		.when('/articles/:id', {
			controller: 'homeController',
			templateUrl: 'partials/home/articles.html'
		})
		.when('/login', {
			controller: 'loginController',
			templateUrl: 'partials/home/login.html'
		})
		.when('/register', {
			controller: 'registerController',
			templateUrl: 'partials/home/register.html'
		})
		.when('/user/admin/:username', {
			controller: 'adminController',
			templateUrl: 'partials/admin/adminPage.html'
		})
		.when('/user/admin/:username/users', {
			controller: 'adminController',
			templateUrl: 'partials/admin/users.html'
		})
		.when('/user/admin/:username/users/:delivererId/add', {
			controller: 'adminController',
			templateUrl: 'partials/admin/addVehicle.html'
		})
		.when('/user/admin/:username/users/:delivererEditId/edit', {
			controller: 'adminController',
			templateUrl: 'partials/admin/editVehicle.html'
		})
		.when('/user/admin/:username/restaurants', {
			controller: 'adminController',
			templateUrl: 'partials/admin/restaurants.html'
		})
		.when('/user/admin/:username/restaurants/add', {
			controller: 'adminController',
			templateUrl: 'partials/admin/addRestaurant.html'
		})
		.when('/user/admin/:username/restaurants/edit/:id', {
			controller: 'adminController',
			templateUrl: 'partials/admin/editRestaurant.html'
		})
		.when('/user/admin/:username/restaurants/:restaurantId/articles', {
			controller: 'adminController',
			templateUrl: 'partials/admin/articles.html'
		})
		.when('/user/admin/:username/restaurants/add/articles/:selectedRestId', {
			controller: 'adminController',
			templateUrl: 'partials/admin/addArticle.html'
		})
		.when('/user/admin/:username/restaurants/articles/:selRestId/edit/:articleId', {
			controller: 'adminController',
			templateUrl: 'partials/admin/editArticle.html'
		})
		.when('/user/admin/:username/orders', {
			controller: 'adminController',
			templateUrl: 'partials/admin/orders.html'
		})
		.when('/user/admin/:username/orders/:orderId', {
			controller: 'adminController',
			templateUrl: 'partials/admin/editOrder.html'
		})
		.when('/user/customer/:username', {
			controller: 'customerController',
			templateUrl: 'partials/customer/customerPage.html'
		})
		.when('/user/customer/:username/restaurants/:restaurantId/articles', {
			controller: 'customerController',
			templateUrl: 'partials/customer/articles.html'
		})
		.when('/user/customer/:username/orders', {
			controller: 'customerController',
			templateUrl: 'partials/customer/orders.html'
		})
		.when('/user/customer/:username/cart/:restId', {
			controller: 'customerController',
			templateUrl: 'partials/customer/cart.html'
		})
		.when('/user/deliverer/:username', {
			controller: 'delivererController',
			templateUrl: 'partials/deliverer/delivererPage.html'
		})
		.when('/user/deliverer/:username/orders', {
			controller: 'delivererController',
			templateUrl: 'partials/deliverer/orders.html'
		})
		.when('/user/deliverer/:username/vehicle', {
			controller: 'delivererController',
			templateUrl: 'partials/deliverer/vehicle.html'
		})
}]);

app.config(function ($logProvider) {
	$logProvider.debugEnabled(true);
});