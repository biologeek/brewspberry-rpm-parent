var ingredientApp = angular.module('ingredientApp', ['ngMessages']);


ingredientApp.controller('ingredientController', function ($scope){
	
	
	/*
	 * webservice submission format :
	 * Request : POST
	 * JSON = 
	 *	{
	 *		"id" : 0, 
	 *		"type" : "malt",
	 *		"provider" : "Weyermann",
	 *		"description" : "Blablablabla blablalerkzj 12 EBC blablabla"
	 *
	 *  }
	 *
	 *
	 */
	var submitToIngredientService = function(){
		
		
		$scope.requestObject = {};
		
		
		$scope.requestObject["id"] = ingredient_id;
		$scope.requestObject["type"] = ingredient_type;
		$scope.requestObject["provider"] = ing_fournisseur;
		$scope.requestObject["description"] = ing_description;
		
		
		
	}
	
	
	
	var sendRequest = function(serviceURL, postContent, callback){
		
		
		$http.post(serviceURL, postContent, function(){
		
		
		
		}};
		
		
	}
	
	
});