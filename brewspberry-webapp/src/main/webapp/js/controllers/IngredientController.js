(function() {
	'use strict';

	angular.module('brewspberry').controller('IngredientController',
			IngredientController);

	IngredientController.$inject = [ '$scope', 'IngredientService',
			'UtilsService', 'CONSTANTS' ];

	function IngredientController($scope, IngredientService, UtilsService,
			CONSTANTS) {

		var vm = this;

		vm.listOfIngredients = [];
		vm.units = [];

		vm.floculationLevels = CONSTANTS.FLOCULATION_LEVELS;

		vm.types = CONSTANTS.ING_TYPES;

		vm.hopTypes = CONSTANTS.HOP_TYPES;

		var getAllIngredients = function() {

			IngredientService.getAllIngredients(function(response) {

				vm.listOfIngredients = response.data;

			}, function(response) {

				vm.showSuccess = false;
				vm.showErrors = true;

				vm.submissionFailureMessage = vm.submissionFailureMessage
						+ "\n Could not get ingredients !";

			});

		}

		vm.processIngredient = function(ingredientForm) {
			console.log("ici")
			if (ingredientForm.$dirty && ingredientForm.$valid) {

				IngredientService
						.save(
								vm.toCreateIngredient,
								function(response) {

									vm.showSuccess = true;
									vm.showErrors = false;

									vm.submissionSuccessMessage = "Ingredient created with ID : "
											+ response.data.id;
									// Adding to current list
									vm.listOfIngredients.push(response.data);
								},
								function(response) {

									vm.showSuccess = false;
									vm.showErrors = true;

									vm.submissionFailureMessage = "Error at creation : "
											+ response.data;
								});

			}
		}

		function getAllUnits() {

			UtilsService.getAllUnits(function(response) {

				vm.units = response.data;

			}, function(response) {

				vm.showSuccess = false;
				vm.showErrors = true;

				vm.submissionFailureMessage = vm.submissionFailureMessage
						+ "Could not retrieve units : " + response.statusCode;

			})

		}

		function init() {

			getAllIngredients();

			getAllUnits();

		}

		init();
	}
})();