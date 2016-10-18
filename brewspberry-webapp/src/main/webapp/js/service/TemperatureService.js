

(function (){
	'use strict';
	
	
	angular.module('brewspberry').factory('TemperatureService', TemperatureService);
	
	
	TemperatureService.$inject = ['$http', 'CONSTANTS'];
	
	function TemperatureService($http, CONSTANTS){

		
		var vm = this;

		TemperatureService.initTemperaturesForStep = function(stepID, uuid, callBackSuccess, callbackError){

			var promise = $http({
				method: 'GET',
				//url : 'js/tests/mockStepTemps.json'
				url: CONSTANTS.TEMP_SERVICE_URL+'/step/'+stepID+'/act/'+uuid

			}).then(function (response) {
				callBackSuccess(response);
			}, function (response) {
				callbackError(response);
			});

			return promise;
		};

		TemperatureService.updateTemperaturesForStep = function (stepID, uuid, last, callBackSuccess, callbackError){

			var promise = $http({
				method: 'GET',
				url : 'js/tests/mockStepTemps.json'
				//url: CONSTANTS.TEMP_SERVICE_URL+'/step/'+stepID+'/act/'+uuid+'/last/'+last,

			}).then(function (response) {
				callBackSuccess(response);
			}, function (response) {
				callbackError(response);
			});

			return promise;
		};
			
			return TemperatureService;
	}

})();