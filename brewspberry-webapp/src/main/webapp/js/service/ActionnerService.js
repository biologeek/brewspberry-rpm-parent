/**
 * Created by xcaron on 20/09/2016.
 */

(function() {

	'use strict';

	angular.module('brewspberry').factory('BrewService', BrewService);

	BrewService.$inject = [ '$http', 'CONSTANTS' ];

	function BrewService($http, CONSTANTS) {

		var BrewServiceFactory = {};
		
		BrewServiceFactory.getAvailableActionners = function(callBackSucces, callBackError){
			
			var promise = $http({
				method : 'GET',
				//url : 'js/tests/mock.json'
				url: CONSTANTS.ACTIONNER_SERVICE_URL+'/available',

			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callbackError(response);
			});

			return promise;
			
		}
		
		return BrewServiceFactory;
		
	}
})();