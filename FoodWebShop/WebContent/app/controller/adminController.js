app.controller('adminController', function ($scope, userService, restaurantService, orderService, $window, $route, $routeParams) {

	function init() {

		userService.getCurrentUser().success(function (data) {
			$scope.user = data;
		});

		userService.getUsers().success(function (data) {
			$scope.users = data;
		});

		restaurantService.getRestaurants().success(function (data) {
			$scope.restaurants = data;
		});

		$scope.selectedId = $routeParams.id;

		restaurantService.getRestaurantById($scope.selectedId).success(function (data) {
			$scope.restaurantToEdit = data;
		});

		$scope.restaurantId = $routeParams.restaurantId;

		$scope.selectedRestId = $routeParams.selectedRestId;

		$scope.selRestId = $routeParams.selRestId;
		$scope.selectedArtId = $routeParams.articleId;

		restaurantService.getArticleById($scope.selRestId, $scope.selectedArtId).success(function (data) {
			$scope.articleToEdit = data;
		});

		$scope.delivererId = $routeParams.delivererId;

		$scope.vehicleId = $routeParams.vehicleId;

		$scope.delivererEditId = $routeParams.delivererEditId;

		userService.getUser($scope.delivererEditId).success(function (data) {
			$scope.vehiclePlaceholder = data.vehicle;
		});

		restaurantService.getRestaurantById($scope.restaurantId).success(function (data) {
			$scope.selectedRestaurant = data;
		});

		restaurantService.getRestaurantById($scope.selectedRestId).success(function (data) {
			$scope.selectedRest = data;
		});

		orderService.getAllOrders().success(function (data) {
			$scope.orders = data;
		});

		$scope.orderToEditId = $routeParams.orderId;

		orderService.getOrderById($scope.orderToEditId).success(function (data) {
			$scope.orderToEdit = data;
		});

		userService.getCustomers().success(function (data) {
			$scope.customers = data;
		});

		userService.getDeliverers().success(function (data) {
			$scope.deliverers = data;
			if ($scope.orderToEdit.deliveryPersonId !== undefined) {
				$scope.deliverers.push($scope.orderToEdit.deliveryPersonId);
			}
		});
	}

	init();

	$scope.logoutUser = function () {
		userService.logout().success(function () {
			toastr.warning("Goodbye " + $scope.user.firstName);
			$window.location.href = '/FoodWebShop/#/';
		});
	}

	$scope.setAdmin = function (username) {
		userService.changeRole('admin', username).success(function () {
			$route.reload();
		});
	}

	$scope.setCustomer = function (username) {
		userService.changeRole('customer', username).success(function () {
			$route.reload();
		});
	}

	$scope.setDeliverer = function (username) {
		userService.changeRole('deliverer', username).success(function () {
			$route.reload();
		});
	}

	$scope.deleteRestaurant = function (id) {
		restaurantService.deleteRestaurant(id).success(function () {
			$route.reload();
		});
	}

	$scope.deleteArticle = function (articleId, restId) {
		restaurantService.deleteArticle(articleId, restId).success(function () {
			$route.reload();
		});
	}

	$scope.deleteVehicle = function (vehicleId, username) {
		restaurantService.deleteVehicle(vehicleId, username).success(function () {
			$route.reload();
		});
	}

	$scope.restaurant = {
		name: '',
		address: '',
		category: ''
	};

	$scope.arcicle = {
		name: '',
		price: '',
		description: '',
		amount: '',
		type: ''
	};

	$scope.vehicle = {
		manufacturer: '',
		model: '',
		type: '',
		registration: '',
		productionYear: '',
		used: '',
		note: ''
	};

	$scope.categories = ['DOMESTIC', 'BARBECUE', 'CHINESE', 'INDIAN', 'PASTRY', 'PIZZERIA'];

	$scope.types = ['FOOD', 'BEVERAGE'];

	$scope.vehicleTypes = ['BICYCLE', 'SCOOTER', 'CAR'];

	$scope.statuses = ['ORDERED', 'DELIVERY_IN_PROGRESS' ,'DELIVERED', 'CANCELED'];

	$scope.addRestaurant = function (restaurant) {
		restaurantService.addRestaurant(restaurant).success(function (data) {
			toastr.success(data);
			$window.location.href = '/FoodWebShop/#/user/admin/' + $scope.user.username + '/restaurants';
		}).error(function (data, status) {
			console.error('Repos error', status, data);
			toastr.warning('Please enter restaurant name, address and category');
		});
	}

	$scope.updateRestaurant = function (restaurant) {
		restaurantService.updateRestaurant(restaurant, $scope.selectedId).success(function (data) {
			toastr.success(data);
			$window.location.href = '/FoodWebShop/#/user/admin/' + $scope.user.username + '/restaurants';
		}).error(function (data, status) {
			console.error('Repos error', status, data);
			toastr.warning('Please enter restaurant name, address and category');
		});
	}

	$scope.addArticle = function (article, restId) {
		restaurantService.addArticle(article, restId).success(function (data) {
			toastr.success(data);
			$window.location.href = '#/user/admin/' + $scope.user.username + '/restaurants/' + $scope.selectedRest.id + '/articles';
		}).error(function (data, status) {
			console.error('Repos error', status, data);
			toastr.warning('Please fill all fields');
		});
	}

	$scope.updateArticle = function (articleId, restaurantId, article) {
		restaurantService.updateArticle(articleId, restaurantId, article).success(function (data) {
			toastr.success(data);
			$window.location.href = '#/user/admin/' + $scope.user.username + '/restaurants/' + restaurantId + '/articles';
		}).error(function (data, status) {
			console.error('Repos error', status, data);
			toastr.warning('Please fill all fields');
		});
	}

	$scope.addVehicle = function (vehicle, username) {
		restaurantService.addVehicle(vehicle, username).success(function (data) {
			toastr.success(data);
			$window.location.href = '#/user/admin/' + $scope.user.username + '/users';
		}).error(function (data, status) {
			console.error('Repos error', status, data);
			toastr.warning('Please fill all fields');
		});
	}

	$scope.updateVehicle = function (vehicleId, username, vehicle) {
		restaurantService.updateVehicle(vehicleId, username, vehicle).success(function (data) {
			toastr.success(data);
			$window.location.href = '#/user/admin/' + $scope.user.username + '/users';
		}).error(function (data, status) {
			console.error('Repos error', status, data);
			toastr.warning('Please fill all fields');
		});
	}
});