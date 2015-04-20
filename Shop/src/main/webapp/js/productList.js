/**
 * 
 */
shopApp.registerProductList('productListController', function($http, $scope,
		$routeParams) {

	var url = "getAllProductList.htm";
	$http.get(url).success(function(data) {
		$scope.pageInform = data;
	})
});