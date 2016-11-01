(function() {
	'use strict';

	angular.module('brewspberry').controller('ActionnerController',
			AccueilController);

	AccueilController.$inject = [ '$scope', 'ActionnerService' ];

	function AccueilController($scope, ActionnerService) {

		var vm = this;

		vm.genericActionners = [];
		vm.pins = [];
		vm.types = [];
		vm.showSuccess = false;
		vm.showErrors = false;
		
		
		var initPage = function(){
			getActionnerTypes();
			getPins();
			vm.getActionners();
		}
		
		var getActionnerTypes = function(){
			
			ActionnerService.getActionnerTypes(function(response){
				
				vm.types = response.data;
				
			}, function(response){
				
				vm.showSuccess = false;
				vm.showErrors = true;
				
				vm.submissionFailureMessage = vm.submissionFailureMessage+ "<br /> Could not get actionner types !";
				
			});
		}

		var getPins= function(){
			
			ActionnerService.getPins(function(response){
				
				vm.pins = response.data;
				
			}, function(response){
				
				vm.showSuccess = false;
				vm.showErrors = true;
				
				vm.submissionFailureMessage = vm.submissionFailureMessage+ "<br /> Could not get pins !";
				
			});
		}
		
		vm.getActionners = function(){
			
			ActionnerService.getAllActionners(function(response){
				
				vm.genericActionners = response.data;
				
			}, function(response){

				vm.showSuccess = false;
				vm.showErrors = true;
				
				vm.submissionFailureMessage = vm.submissionFailureMessage+ "<br /> Could not get actionners !";

				
			});
			
		}

		vm.processActionner = function(actionnerForm) {

			if (actionnerForm.$dirty && actionnerForm.$valid) {

				ActionnerService
						.save(
								vm.toCreateActionner,
								function(response) {

									vm.showSuccess = true;
									vm.showErrors = false;

									vm.submissionSuccessMessage = "Actionner created with ID : "
											+ response.data.id;
									// Adding to current list
									vm.genericActionners.push(response.data);
								},
								function(response) {

									vm.showSuccess = false;
									vm.showErrors = true;

									vm.submissionFailureMessage = "Error at creation : "
											+ response.data;
								});

			}
		}
		
		initPage();

	}
})();