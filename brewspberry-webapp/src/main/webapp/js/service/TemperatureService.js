

(function (){
	'use strict';
	
	
	angular.module('brewspberry').factory('TemperatureService', TemperatureService);
	
	
	TemperatureService.$inject = ['$http', 'CONSTANTS'];
	
	function TemperatureService($http, CONSTANTS){
		
		
		
		
		var vm = this;
		
		
		
		vm.initTemperatureForStep = function(stepID){
			
			
			
		}
		
	}
	
	
})();