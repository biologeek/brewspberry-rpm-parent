/**
 * Created by xcaron on 21/09/2016.
 */

(function() {

	'use strict';

	angular.module('brewspberry').controller('BrewController', BrewController);

	BrewController.$inject = [ '$scope', 'BrewService', '$routeParams' ];

	function BrewController($scope, BrewService, $routeParams) {

		var vm = this;
		vm.currentFullBrew = {};

		vm.showErrors = false;
		vm.showSuccess = false;
		var brewID = $routeParams.brewID;

		/**
		 * On page loaded, retrieves steps list and feeds view
		 */
		$scope.$on('$viewContentLoaded', function() {

			BrewService.getBrew(brewID, function(response) {
				/**
				 * In case of success
				 */
				vm.currentFullBrew = response.data;

			}, function(response) {
				/**
				 * In case of error
				 */

				vm.showErrors = true;

				vm.submissionFailureMessage = "Code : " + response.statusCode
						+ ". Message : " + response.data;

			})

		});

		
		/**
		 * 
		 * 
		 * 
		 * 
		 */
		vm.changeActionnerState = function(actionerID, stepID, index) {

			if (vm.currentFullBrew.steps[stepID].actioners[index].class == 'buttonOn') {
				TemperatureService
						.deactivate(
								actionerID,
								function(response) {
									/*
									 * In case of success, changing picture
									 */

									vm.currentFullBrew.steps[stepID].class = 'buttonOff';

									/*
									 * 
									 * switch (actionnerType) {
									 * 
									 * case '1':
									 * 
									 * $document('#' + buttonID).attr('src',
									 * 'images/thermo-off.jpg').attr('class',
									 * 'buttonOff');
									 * 
									 * break; case '2':
									 * 
									 * $document('#' + buttonID).attr('src',
									 * 'images/pump-off.jpg').attr('class',
									 * 'buttonOff');
									 * 
									 * break; case '3':
									 * 
									 * $document('#' + buttonID).attr('src',
									 * 'images/engine-off.jpg').attr('class',
									 * 'buttonOff');
									 * 
									 * break; }
									 * 
									 */
								},
								function(response) {

									/*
									 * In case of error, display error message
									 */

									vm.showErrors = true;

									vm.submissionFailureMessage = "Could not deactivate. Error : "
											+ response.statusText
											+ ", "
											+ response.data;

								});

			} else if (vm.currentFullBrew.steps[stepID].class == 'buttonOff') {

				TemperatureService
						.activate(
								actionerID,
								function(response) {
									/*
									 * In case of success, changing picture
									 */
									vm.currentFullBrew.steps[stepID].actioners[index].class = 'buttonOn';

									/*
									 * switch (actionnerType) {
									 * 
									 * case '1':
									 * 
									 * $document('#' + buttonID).attr('src',
									 * 'images/thermo-on.jpg').attr('class',
									 * 'buttonOn');
									 * 
									 * break; case '2':
									 * 
									 * $document('#' + buttonID).attr('src',
									 * 'images/pump-on.jpg').attr('class',
									 * 'buttonOn');
									 * 
									 * break; case '3':
									 * 
									 * $document('#' + buttonID).attr('src',
									 * 'images/engine-on.jpg').attr('class',
									 * 'buttonOn');
									 * 
									 * break; }
									 */
								},
								function(response) {

									/*
									 * In case of error, display error message
									 */

									vm.showErrors = true;

									vm.submissionFailureMessage = "Could not activate. Error : "
											+ response.statusText
											+ ", "
											+ response.data;

								});
			} else {
				vm.showErrors = true;

				vm.submissionFailureMessage = "Operation not permitted";
			}
		}
		
		
		
		/**
		 * 
		 * 
		 */
		vm.addAStep = function(addAStepForm){
			
			
			var addedStep = addAStepForm.step;
			
			// Service call
			BrewService.addStepToBrew(vm.currentFullBrew, step, function(response){
				
				vm.showSuccess = true;
				vm.showErrors = false;
				
				
				vm.submissionSuccessMessage = "Step added to brew";
				
				
				//Adding step to current steps list
				vm.currentFullBrew.steps.push(response.data);
				
			}, function(response){

				vm.showSuccess = false;
				vm.showErrors = true;
				
				
				vm.submissionSuccessMessage = "Step could not be added : "+response.statusText+", "+response.data;
				
				
			});
			
			
		}
		
		
		
		
		vm.initCharts = function(){
			
			/*
			 * TODO : feed step.chart var to display temperatures
			 * 1. Get temperatures from service for active step
			 * 2. Add it to step object
			 * 
			 * 
			 */
			
			if (typeof(vm.currentFullBrew) != 'undefined'){
				
				var steps = vm.currentFullBrew.steps;
				
				var counter = 0;
				for(step in steps){
					// For each step, if step is active, initiates
					if (step.isActive){
						TemperatureService.initTemperaturesForStep(step.id, function(response){
							
							/*
							 * Receiving object : 
							 * [
							 * 		{
							 * 			id : 1,
							 * 			value : 20.0,
							 * 			date : 123456789123456, //date in milliseconds
							 * 			uuid : '123456azerty456987',
							 * 			name : 'PROBE1',
							 * 			actionner : 3,
							 * 			step : 4,
							 * 			brew : 1
							 * 		} , ...
							 * ]
							 * 
							 */
							
							vm.currentFullBrew.steps[counter].chart = getSeriesFromServiceList(response.data);
							vm.currentFullBrew.steps[counter].chart.options = {
									scales : {
										yAxes : [
										         {
										        	 id : 'yAxis', 
										        	 type: 'linear',
										             display: true,
										             position: 'left'
										         }
										         ]
									},
									legend : {
										display : true,
										labels : {
											fontSize : 10
										}
									}
							}
							
							}, function(response){
								
								
								
							});
					}
					
					counter++;
					
				}
				
				
			}
			
		}
		
		
		
		/**
		 * Returns distinct list of probes to feed chart series
		 */
		vm.getSeriesFromServiceList = function(serviceObject){
			
			result={series : [], data : [], labels : []};
			for (obj in serviceObject){
				
				var serie = result.series.indexOf(obj.uuid)
				if (serie == -1){
					
					result.series.push(obj.name);
					serie = result.series.indexOf(obj.uuid) 
				}
				
				result.data[serie].push(obj.value);
				result.data[serie].push(new Date(obj.date));
				
			}		
			
			return result;
		}

	}

})();