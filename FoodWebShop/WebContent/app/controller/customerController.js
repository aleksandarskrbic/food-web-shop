app.controller('customerController', function ($scope, userService, orderService, restaurantService, $window, $route, $routeParams) {

	function init() {
		userService.getCurrentUser().success(function (data) {
			$scope.user = data;
		});

		restaurantService.getRestaurants().success(function (data) {
			$scope.restaurants = data;
		});

		$scope.restaurantId = $routeParams.restaurantId;

		$scope.username = $routeParams.username;

		$scope.restId = $routeParams.restId;

		restaurantService.getRestaurantById($scope.restaurantId).success(function (data) {
			$scope.selectedRestaurant = data;
		});

		userService.getUser($scope.username).success(function (data) {
			$scope.favourites = data.restaurants;
			$scope.bonusPoints = data.bonusPoints;
		});

		orderService.getUserOrders($scope.username).success(function (data) {
			$scope.orders = data;
		}).finally(function () {
			$scope.total = 0;
			for (var i = 0; i < $scope.orders.length; i++) {
				$scope.total += $scope.orders[i].bill;
			}
		});

		orderService.getItems().success(function (data) {
			$scope.items = data;
		});
	}

	init();

	$scope.portions = 1;
	$scope.note = '';
	$scope.bp = 0;

	$scope.isFavourite = function (id) {
		if ($scope.favourites == undefined) {
			return false;
		}

		return $scope.favourites.indexOf(id) !== -1;
	}

	$scope.addToCart = function (article, portions) {
		if (portions < 1) {
			toastr.error("Number of portions have to be minumum 1");
		} else {
			var item = {
				article: article,
				amount: portions
			};

			$scope.portions = 1;

			orderService.addItem(item).success(function () {
				toastr.success('Item added to Cart');
			});
		}
	}

	$scope.removeFromCart = function (id) {
		orderService.removeItem(id).success(function () {
			toastr.error('Item removed from Cart');
		}).finally(function () {
			orderService.getItems().success(function (data) {
				$scope.items = data;
			});
		});

		$window.location.href = '/FoodWebShop/#/user/customer/' + $scope.user.username + '/cart/' + $scope.restId;
	}

	$scope.addToFavourites = function (restaurantId) {
		userService.addToFavourites($scope.user.username, restaurantId).success(function () {
			$route.reload();
		});
	}

	$scope.removeFromFavourites = function (restaurantId) {
		userService.removeFromFavourites($scope.user.username, restaurantId).success(function () {
			$route.reload();
		});
	}

	$scope.logoutUser = function () {
		userService.logout().success(function () {
			toastr.warning("Goodbye " + $scope.user.firstName);
			$window.location.href = '/FoodWebShop/#/';
		});
	}

	$scope.makeOrder = function (note, bp, items) {
		 if (bp > $scope.bonusPoints || bp < 0) {
			toastr.error("Please enter valid number of bonus points");
		} else {
			var price = 0;
			for (var i = 0; i < items.length; i++) {
				price += items[i].article.price * items[i].amount;
			}

			var curretBonusPoints = $scope.bonusPoints;

			if (bp === 0 && price > 500) {
				curretBonusPoints++;
			}

			if (bp > 0 && bp <= $scope.bonusPoints) {
				price = ((1.0 - (0.03 * bp)) * price);
				curretBonusPoints = -bp;
				if (price > 500) {
					curretBonusPoints += 1;
				}
			}

			if (curretBonusPoints > 10) {
				curretBonusPoints = 10;
			}

			var order = {
				items: $scope.items,
				restaurantId: $scope.restId,
				userId: $scope.user.username,
				note: note,
				bill: price,
				bonusPoints: curretBonusPoints
			};

			orderService.addOrder($scope.user.username, order).success(function (data) {
				toastr.success(data);
				$window.location.href = '#/user/customer/' + $scope.user.username;
			}).error(function (data, status) {
				console.error('Repos error', status, data);
				toastr.warning('Please fill all fields');
			}).finally(function () {
				orderService.emptyCart().success(function () {});
			});
		}
	}
});