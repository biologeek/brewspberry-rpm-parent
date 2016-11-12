/**
 * Created by xcaron on 20/09/2016.
 */

(function() {

	'use strict';

	angular.module('brewspberry').factory('ActionnerService', ActionnerService);

	ActionnerService.$inject = [ '$http', 'CONSTANTS' ];

	function ActionnerService($http, CONSTANTS) {

		var ActionnerServiceFactory = {};

		ActionnerServiceFactory.getAvailableActionners = function(
				callBackSuccess, callBackError) {

			var promise = $http({
				method : 'GET',
				// url : 'js/tests/mock.json'
				url : CONSTANTS.ACTIONNER_SERVICE_URL + '/available',

			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callBackError(response);
			});

			return promise;

		}
		
		ActionnerServiceFactory.getAllActionners = function(callBackSuccess, callBackError){

			var promise = $http({
				method : 'GET',
				// url : 'js/tests/mock.json'
				url : CONSTANTS.ACTIONNER_SERVICE_URL+'/',
			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callBackError(response);
			});

			
		}

		ActionnerServiceFactory.save = function(object, callBackSuccess,
				callBackError) {

			var promise = $http({
				method : 'POST',
				// url : 'js/tests/mock.json'
				url : CONSTANTS.ACTIONNER_SERVICE_URL+'/save',
				data : object,

			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callBackError(response);
			});

			return promise;

		}
		
		ActionnerServiceFactory.getPins =  function(callBackSuccess, callBackError){

			var promise = $http({
				method : 'GET',
				// url : 'js/tests/mock.json'
				url : CONSTANTS.ACTIONNER_SERVICE_URL+'/pins',

			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callBackError(response);
			});

			return promise;

			
		}
		

		
		ActionnerServiceFactory.getActionnerTypes =  function(callBackSuccess, callBackError){

			var promise = $http({
				method : 'GET',
				// url : 'js/tests/mock.json'
				url : CONSTANTS.ACTIONNER_SERVICE_URL+'/types',

			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callBackError(response);
			});

			return promise;

			
		}

		
		ActionnerServiceFactory.activate(actId, callBackSuccess, callBackError){

			var promise = $http({
				method : 'POST',
				// url : 'js/tests/mock.json'
				url : CONSTANTS.ACTIONNER_SERVICE_URL+'/activate/'+actId,

			}).then(function(response) {
				callBackSuccess(response);
			}, function(response) {
				callBackError(response);
			});

			return promise;

			
		}
		
		ActionnerServiceFactory.deactivate(actId, callBackSuccess, callBackError){

			var promise = $http({
				method : 'POST',
				// url : 'js/tests/mock.json'
				url : CONSTANTS.ACTIONNER_SERVICE_URL+'/deactivate/'+actId,

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