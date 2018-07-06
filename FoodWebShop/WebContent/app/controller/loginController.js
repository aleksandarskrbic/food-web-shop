app.controller('loginController', function ($scope, userService, $window) {

	$scope.user = {
		username: '',
		password: ''
	};

	$scope.login = function (user) {
		userService.login(user).success(function (data) {
			if (data.username === undefined || data.password === undefined) {
				toastr.error("Wrong username or password");
			} else {
				toastr.info('Welcome ' + data.firstName);

				if (data.role === "ADMIN") {
					$window.location.href = '/FoodWebShop/#/user/admin/' + data.username;
				}

				if (data.role === "CUSTOMER") {
					$window.location.href = '/FoodWebShop/#/user/customer/' + data.username;
				}

				if (data.role === "DELIVERER") {
					$window.location.href = '/FoodWebShop/#/user/deliverer/' + data.username;
				}
			}
		});
	};
});