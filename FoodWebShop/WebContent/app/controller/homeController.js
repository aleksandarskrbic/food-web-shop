app.controller('homeController', function ($scope, restaurantService, $routeParams) {

	function init() {
		restaurantService.getRestaurants().success(function (data) {
			$scope.restaurants = data;
		});

		var cate = $routeParams.category;

		restaurantService.getRestaurantsByCategory(cate).success(function (data) {
			if (cate != undefined) {
				$scope.cat = cate.charAt(0).toUpperCase() + cate.substr(1);
			}
			$scope.restaurantsByCategory = data;
		});

		restaurantService.getPopularDrinks().success(function (data) {
			$scope.drinks = data;
		});

		restaurantService.getPopularFood().success(function (data) {
			$scope.food = data;
		});

		$scope.selectedId = $routeParams.id;

		restaurantService.getRestaurantById($scope.selectedId).success(function (data) {
			$scope.selectedRestaurant = data;
		});
	}

	init();

});