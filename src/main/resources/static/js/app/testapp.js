var testApp = angular.module("testApp",['ui.bootstrap']);

testApp.controller('MainController', function($scope,  $http) {
	
	$scope.itemsPerPage = 10;
	$scope.totalCount = 0;
	$scope.companies = [];
	$scope.loadData = false;
    
    $scope.scrapeAndGetData = function(){
    	$scope.companies = [];
    	$scope.totalCount = 0;
    	$scope.loadData = true;
    	$http({
			method: 'GET',
			url: "scrape",
			headers: {
		            'Content-Type': 'application/json'
			}
		}).then(function(result){
			$scope.currentStartItem = 1;
			
			$scope.loadData = false;
			var data = result.data;
			$scope.totalCount = data.totalCount;
			$scope.currentEndItem = $scope.itemsPerPage > $scope.totalCount ? $scope.totalCount : 10;
			$scope.companies = data.data;
		}, function(error){
			//TODO: fire error
		});
    	
    }
    
    $scope.init = function() {
    	$scope.loadData = true;
    	$http({
			method: 'GET',
			url: "getData",
			params: {page:0},
			headers: {
		            'Content-Type': 'application/json'
			}
		}).then(function(result){
			$scope.currentStartItem = 1;
			$scope.loadData = false;
			var data = result.data;
			$scope.totalCount = data.totalCount;
			$scope.currentEndItem = $scope.itemsPerPage > $scope.totalCount ? $scope.totalCount : 10;
			$scope.companies = data.data;
		}, function(error){
			//TODO: fire error
		});
	}
    
    $scope.getOtherPage = function(){
    	$http({
			method: 'GET',
			url: "getData",
			params: {page:$scope.currentPage -1},
			headers: {
		            'Content-Type': 'application/json'
			}
		}).then(function(result){
			$scope.currentStartItem = ($scope.currentPage -1) *10 + 1;
			$scope.loadData = false;
			var data = result.data;
			$scope.totalCount = data.totalCount;
			$scope.currentEndItem = ($scope.currentPage -1) *10 + $scope.itemsPerPage > $scope.totalCount ? $scope.totalCount : ($scope.currentPage -1) *10 + $scope.itemsPerPage;
			$scope.companies = data.data;
		}, function(error){
			//TODO: fire error
		});
    }
    
    
    $scope.init();
	
});