/**
 * 
 */
shopApp
		.registerCategories(
				'categoriesController',
				[
						'$scope',
						'$http',
						function($scope, $http) {
							var url = "populateDropDownList/type.htm";
							$http({
								method : 'get',
								url : url
							}).success(function(data) {
								$scope.types = data;
							});

							$scope.getGenres = function() {
								if ($scope.selectedType) {
									var type = $scope.selectedType.type;
									if (type === null || type === undefined) {
										alert("Type does not exist");
										return;
									}
									var url = 'populateDropDownList/type/'
											+ type + '.htm'
									$http.get(url).success(function(data) {
										$scope.genres = data;
									});
								}

							}

							$scope.getProductListDropDown = function() {

								if ($scope.selectedType && $scope.selectedGenre) {
									var type = $scope.selectedType.type;
									if (type === null || type === undefined) {
										alert("Type does not exist");
										return;
									}
									var genre = $scope.selectedGenre.genre;
									if (genre === null || genre === undefined) {
										alert("Genre does not exist");
										return;
									}
									var pagelink = 'getProductList/type/';
									var url = pagelink + type + '/genre/'
											+ genre + '.htm'
									$http.get(url).success(function(data) {
										$scope.pageInform = data;
									});
								}
							}
							$scope.getProductListSearch = function() {
								var name = $scope.selectedText;
								if (name === null || name === undefined) {
									alert("Name does not exist");
									return;
								}
								var pagelink = "getProductList/name/";
								var url = pagelink + name + ".htm";
								$http.get(url).success(function(data) {
									$scope.pageInform = data;
								});
							}
							$scope.addToCart = function() {
								$scope.isChecked = {};
								var url = "addToCart.htm";
								var products = [];
								for (var i = 0; i < $scope.pageInform[0].productList.length; i++) {
									if ($scope.pageInform[0].productList[i].isChecked) {
										type = $scope.pageInform[0].productList[i].type;
										genre = $scope.pageInform[0].productList[i].genre;
										name = $scope.pageInform[0].productList[i].name;
										products.push(type, genre, name);
										$scope.pageInform[0].productList[i].isChecked = false;
									}
								}
								var productsJSON = JSON.stringify(products);
								$http({
									method : 'post',
									url : url,
									data : productsJSON
								})
										.success(
												function() {
													alert("Product(s) was(re) successfully added to your cart");
												})
							}
						} ])