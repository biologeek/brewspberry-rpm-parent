(function() {

	'use strict';

	angular.module('brewspberry').controller('BrewCreationController',
			BrewCreationController);

	BrewCreationController.$inject = [ '$scope', 'BrewService', '$routeParams',
			'CONSTANTS' ];

	function BrewCreationController($scope, BrewService, $routeParams,
			CONSTANTS) {

		var vm = this;
		vm.statuses = CONSTANTS.BREW_STATUSES;

		/**
		 * Method in charge of receiving brew object and passing it to service
		 */
		vm.processBrewCreation = function(form) {

			if (form.$dirty) {
				console.log(vm.brew)
				vm.brew.end = vm.brew.endJS.getTime();
				vm.brew.beginning = vm.brew.beginningJS.getTime();

				BrewService.createBrew(vm.brew, function(response) {
					/*
					 * Success, service responds with created brew
					 */

					vm.showErrors = false;
					vm.showSuccess = true;
					
					console.log(vm.showSuccess);
					vm.submissionSuccessMessage = 'Brew created with ID : '
							+ response.data.id;

				}, function(response) {

					/*
					 * Failure
					 */

					vm.showErrors = true;
					vm.showSuccess = false;
					vm.submissionSuccessMessage = 'Errors : ' + response.data;

				});

			}

		}

	}

})();