/**
 * 
 */

(function() {

	'use strict';

	angular.module('brewspberry').factory('StepService', StepService);

	StepService.$inject = [ '$http', 'CONSTANTS' ];

	function StepService($http, CONSTANTS) {

		var StepServiceFactory = {};

		StepServiceFactory.add = function(brewID, step, callbackSuccess,
				callbackError) {
			var promise;

			console.log(step)
			if (brewID > 0 && step != {}) {

				promise = $http({
					method : 'POST',
					url : CONSTANTS.STEP_SERVICE_URL + '/add',
					data : step

				}).then(function(response) {
					callbackSuccess(response);
				}, function(response) {
					callbackError(response);
				});

			}
		}

		StepServiceFactory.startStepForReal = function(stepID, callBackSuccess,
				callBackError) {

			var promise = $http({
				method : 'POST',
				url : CONSTANTS.BREW_SERVICE_URL + '/start/' + stepID
			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callbackError(response);
			});

		}

		StepServiceFactory.stopStepForReal = function(stepID, callBackSuccess,
				callBackError) {

			var promise = $http({
				method : 'POST',
				url : CONSTANTS.BREW_SERVICE_URL + '/stop/' + stepID
			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callbackError(response);
			});

		}
		return StepServiceFactory;

	}

})();