/**
 * 
 */
'use strict';
shopApp.registerShoppingCart('shoppingCartController', function($http, $scope) {
	$scope.removeProduct = function(index) {
		if (index === null || index === undefined || index < 0) {
			alert("Index does not exist");
			return;
		}
		var pagelink = "removeProduct/";
		var url = pagelink + index + ".htm";
		$http.post(url).success(function(data) {
			$scope.pageInform = data;

		})
	}
	var url = "getShoppingCart.htm";
	$http.post(url).success(function(data) {
		$scope.pageInform = data;
	})
});