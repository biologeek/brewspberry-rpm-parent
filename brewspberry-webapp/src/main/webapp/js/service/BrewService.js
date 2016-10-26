/**
 * Created by xcaron on 20/09/2016.
 */

(function() {

	'use strict';

	angular.module('brewspberry').factory('BrewService', BrewService);

	BrewService.$inject = [ '$http', 'CONSTANTS' ];

	function BrewService($http, CONSTANTS) {

		var BrewServiceFactory = {};

		BrewServiceFactory.getAll = function(callBackSuccess, callbackError) {
			var promise = $http({
				method : 'GET',
				//url : 'js/tests/mock.json'
				url: CONSTANTS.BREW_SERVICE_URL+'/light',

			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callbackError(response);
			});

			return promise;
		};
		BrewServiceFactory.getBrew = function(id, isFull, callBackSuccess,
				callbackError) {
			var urlService;
			if (isFull) {
				urlService = CONSTANTS.BREW_SERVICE_URL + '/' + id + '/full';
			} else {
				urlService = CONSTANTS.BREW_SERVICE_URL + '/' + id + '/light';
			}

			var promise = $http({
				method : 'GET',
				//url : 'js/tests/mockFullBrew.json'
				url: urlService,
			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callbackError(response);
			});
			return promise;
		};
		BrewServiceFactory.updateBrew = function(id, obj, callBackSuccess,
				callbackError) {
			var promise = $http({
				method : 'PUT',

				url : CONSTANTS.BREW_SERVICE_URL + '/update/' + id,
				data : obj
			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callbackError(response);
			});
			return promise;
		};

		BrewServiceFactory.createBrew = function(obj, callBackSuccess,
				callbackError) {
			
			var promise = $http({
				method : 'POST',

				url : CONSTANTS.BREW_SERVICE_URL + '/add/simple',
				data : obj
			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callbackError(response);
			});
			return promise;
		};

		BrewServiceFactory.deleteBrew = function(id, callBackSuccess,
				callbackError) {
			var promise = $http({
				method : 'DELETE',
				url : CONSTANTS.BREW_SERVICE_URL + '/delete/' + id,
			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callbackError(response);
			});
			return promise;
		};

		return BrewServiceFactory;
	}
	
})();