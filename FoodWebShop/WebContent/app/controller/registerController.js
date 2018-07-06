app.controller('registerController', function ($scope, userService, $window) {

	$scope.user = {
		username: '',
		password: '',
		firstName: '',
		lastName: '',
		phone: '',
		email: ''
	};

	$scope.addUser = function (user) {
		user.username = user.username.toLowerCase();
		userService.addUser(user).success(function (data) {
			if (data === 'Succesfully registered.') {
				$window.location.href = '/FoodWebShop/#/';
				toastr.success(data);
			} else {
				toastr.error(data);
			}
		});
	};
});