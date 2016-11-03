
(function() {

	'use strict';

	angular.module('brewspberry').factory('IngredientService', IngredientService);

	IngredientService.$inject = [ '$http', 'CONSTANTS' ];

	function IngredientService($http, CONSTANTS) {
		
		var IngredientServiceFactory = {};
		
		IngredientServiceFactory.getAllIngredients = function(callBackSuccess, callBackError){
			

			var promise = $http({
				method : 'GET',
				// url : 'js/tests/mock.json'
				url : CONSTANTS.INGREDIENT_SERVICE_URL+'/',
			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callBackError(response);
			});

			
			
		}
		

		IngredientServiceFactory.save = function(object, callBackSuccess,
				callBackError) {

			var promise = $http({
				method : 'POST',
				// url : 'js/tests/mock.json'
				url : CONSTANTS.INGREDIENT_SERVICE_URL+'/save',
				data : object,

			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callBackError(response);
			});

			return promise;

		}

		return IngredientServiceFactory;
		
		
	}
	
})();