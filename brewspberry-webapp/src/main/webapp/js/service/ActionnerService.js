/**
 * Created by xcaron on 20/09/2016.
 */

(function() {

	'use strict';

	angular.module('brewspberry').factory('ActionnerService', ActionnerService);

	ActionnerService.$inject = [ '$http', 'CONSTANTS' ];

	function ActionnerService($http, CONSTANTS) {

		var ActionnerServiceFactory = {};
		
		ActionnerServiceFactory.getAvailableActionners = function(callBackSuccess, callBackError){
			
			var promise = $http({
				method : 'GET',
				//url : 'js/tests/mock.json'
				url: CONSTANTS.ACTIONNER_SERVICE_URL+'/available',

			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callBackError(response);
			});

			return promise;
			
		}
		
		return ActionnerServiceFactory;
		
	}
})();