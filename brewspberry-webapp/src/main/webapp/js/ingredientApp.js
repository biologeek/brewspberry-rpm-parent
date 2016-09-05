var ingredientApp = angular.module('ingredientApp', [ 'ngMessages' ]);

ingredientApp.controller('ingredientController', function($scope) {

	/*
	 * webservice submission format : Request : POST JSON = { "id" : 0, "type" :
	 * "malt", "provider" : "Weyermann", "description" : "Blablablabla
	 * blablalerkzj 12 EBC blablabla" }
	 * 
	 * 
	 */
	var submitToIngredientService = function() {

		$scope.requestObject = {};

		$scope.requestObject["id"] = $scope.ingredient_id;
		$scope.requestObject["type"] = $scope.ingredient_type;
		$scope.requestObject["provider"] = $scope.ing_fournisseur;
		$scope.requestObject["description"] = $scope.ing_description;

		/* Feeding specific parameters */
		switch ($scope.ingredient_type) {

		case "malt":

			$scope.requestObject["cereal"] = $scope.smal_cereale;
			$scope.requestObject["maltType"] = $scope.smal_type;
			$scope.requestObject["color"] = $scope.smal_couleur;
			$scope.requestObject["variety"] = '';
			$scope.requestObject["alphaAcid"] = '';
			$scope.requestObject["aroma"] = '';
			$scope.requestObject["hopType"] = '';
			$scope.requestObject["specie"] = '';
			$scope.requestObject["foculation"] = '';
			$scope.requestObject["aroma"] = '';
			break;

		case "hop":
			$scope.requestObject["cereal"] = '';
			$scope.requestObject["maltType"] = '';
			$scope.requestObject["color"] = '';
			$scope.requestObject["variety"] = $scope.shbl_variete
			$scope.requestObject["alphaAcid"] = $scope.shbl_acide_alpha
			$scope.requestObject["aroma"] = $scope.shbl_aromes
			$scope.requestObject["hopType"] = $scope.shbl_type
			$scope.requestObject["specie"] = '';
			$scope.requestObject["foculation"] = '';
			$scope.requestObject["aroma"] = '';

			break;

		case "yeast":
			$scope.requestObject["cereal"] = '';
			$scope.requestObject["maltType"] = '';
			$scope.requestObject["color"] = '';
			$scope.requestObject["variety"] = '';
			$scope.requestObject["alphaAcid"] = '';
			$scope.requestObject["aroma"] = '';
			$scope.requestObject["hopType"] = '';
			$scope.requestObject["specie"] = $scope.yeast_specie;
			$scope.requestObject["foculation"] = $scope.slev_floculation;
			$scope.requestObject["aroma"] = $scope.slev_aromes;
			break;

		}

		sendRequest(serviceURL, $scope.requestObject, function(requestSuccess,
				errorMessage) {

			if (requestSuccess) {

				$scope.successfulAdding = true;

			} else {

				$scope.unsuccessfulAdding = true;
				$scope.errorMessage = errorMessage;

			}

		});

	}

	/**
	 * Shows or hide specific ingredient fields depending on menu selection
	 */
	var updatePageWithSpecificFields = function() {

		console.log("Entering updatePageWithSpecificFields");

		console.log("Got : " + value);
		var codeToAppend;

		cleanUpSpecificPart("specific-parameters");

		$scope.malt_specific_parameters_show = false;
		$scope.hop_specific_parameters_show = false;
		$scope.yeast_specific_parameters_show = false;


		$scope.ingredient_id = $scope.requestObject["id"];
		$scope.ingredient_type = $scope.requestObject["type"];
		$scope.ing_fournisseur = $scope.requestObject["provider"];
		$scope.ing_description = $scope.requestObject["description"];


	}

	var sendRequest = function(serviceURL, postContent, callback) {

		$http.post(serviceURL, postContent).then(
				function(response) {

					console.log("Request succeeded");
					callback(true, "");
				},
				function(response) {
					/*
					 * If there's an error, returning status code and response
					 * error
					 */
					console.log("Request failed ! Status code : " + statusText
							+ ", response : " + response.error);
					callback(false, statusText + " " + response.error);
				});
	};

	
	/**
	 * Reveals ingredient specific fields depending on ingredient type sent by API
	 * 
	 * Feeds fields according to received object 
	 *  
	 */
	var feedFieldsForUpdate = function() {

		$scope.malt_specific_parameters_show = false;
		$scope.hop_specific_parameters_show = false;
		$scope.yeast_specific_parameters_show = false;


		$scope.smal_cereale = $scope.requestObject["cereal"];
		$scope.smal_type = $scope.requestObject["maltType"];
		$scope.smal_couleur = $scope.requestObject["color"];
		$scope.shbl_variete = $scope.requestObject["variety"];
		$scope.shbl_acide_alpha = $scope.requestObject["alphaAcid"];
		$scope.shbl_aromes = $scope.requestObject["aroma"];
		$scope.shbl_type = $scope.requestObject["hopType"];
		$scope.yeast_specie = $scope.requestObject["specie"];
		$scope.slev_floculation = $scope.requestObject["foculation"];
		$scope.slev_aromes = $scope.requestObject["aroma"];
		
		$http.get(serviceURL).then(
				function(response) {

					console.log('Success');
					
						switch (response.type){
							
						case 'malt' :
							
							$scope.malt_specific_parameters_show = true;
							
							
							break;
							
						case 'hop' :
							
							$scope.hop_specific_parameters_show = true;
							
							break;
						
						case 'yeast' :
							
							$scope.yeast_specific_parameters_show = true;

							break;
						
						}

				},
				function(response) {

					/*
					 * If there's an error, returning status code and response
					 * error
					 */
					//console.log("Request failed ! Status code : " + statusText
						//	+ ", response : " + response.error);
					
					$scope.errorMessage = 'Code : '+response.statusText + ' Error : '+response.error;
				}

		)

	};

});