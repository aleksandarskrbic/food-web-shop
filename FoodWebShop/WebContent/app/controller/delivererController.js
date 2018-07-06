app.controller('delivererController', function ($scope, userService, orderService, $window, $route) {

	function init() {
		userService.getCurrentUser().success(function (data) {
			$scope.user = data;
			$scope.vehiclePlaceholder = data.vehicle;
		}).finally(function () {
			orderService.getCurrentOrder($scope.user.username).success(function (data) {
				if (data !== undefined) {
					$scope.currentOrder = data;
				}
			}).finally(function () {
				orderService.getPreviousOrders('deliverer', $scope.user.username).success(function (data) {
					$scope.previousOrders = data;
				}).finally(function () {
					$scope.total = 0;
					for(var i=0; i < $scope.previousOrders.length; i++) {
						$scope.total += $scope.previousOrders[i].bill;
					}
				});
			})
		})

		orderService.getPendingOrders().success(function (data) {
			$scope.orders = data;
		});

	}

	init();

	$scope.logoutUser = function () {
		userService.logout().success(function () {
			toastr.warning("Goodbye " + $scope.user.firstName);
			$window.location.href = '/FoodWebShop/#/';
		});
	}

	$scope.takeOrder = function (id, username) {
		orderService.takeOrder(id, username).success(function () {
			toastr.info('Order taken!');
			$route.reload();
		});
	}

	$scope.finishOrder = function () {
		console.log($scope.currentOrder.id + "  " + $scope.user.username);
		orderService.finishOrder($scope.currentOrder, $scope.user.username, $scope.currentOrder.id).success(function () {
			toastr.success('Order Delivered!');
			$route.reload();
		});
	}
});