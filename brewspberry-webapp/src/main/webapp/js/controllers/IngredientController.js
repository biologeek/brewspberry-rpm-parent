(function() {
	'use strict';

	angular.module('brewspberry').controller('ActionnerController',
			AccueilController);

	AccueilController.$inject = [ '$scope', 'IngredientService' ];

	function AccueilController($scope, IngredientService) {

		var vm = this;
		
		vm.listOfIngredients = [];
		
		
		var getAllIngredients = function(){
			
			IngredientService.getAllIngredients (function(response){
				
				vm.listOfIngredients = response.data;
				
			}, function(response){
				
				vm.showSuccess = false;
				vm.showErrors = true;
				
				vm.submissionFailureMessage = vm.submissionFailureMessage+ "<br /> Could not get ingredients !";
				
			});
			
			
		}
		

		vm.processIngredient = function(ingredientForm) {

			if (ingredientForm.$dirty && ingredientForm.$valid) {

				IngredientService
						.save(
								vm.currentIngredient,
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
		
		
		function init(){
			
			getAllIngredients();
			
		}
		
		
		
		init();
	}
})();