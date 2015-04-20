/**
 * 
 */
shopApp.registerGetPage('productListGetPageController', function($http, $scope,
		$routeParams) {

	var pageNumber = $routeParams.pageNumber;
	var pagelink = 'getProductList/page/';
	var url = pagelink + pageNumber + ".htm";
	$http.get(url).success(function(data) {
		$scope.pageInform = data;
	})
});