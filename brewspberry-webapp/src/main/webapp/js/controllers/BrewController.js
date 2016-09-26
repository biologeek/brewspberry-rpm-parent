/**
 * Created by xcaron on 21/09/2016.
 */

(function() {

	'use strict';

	angular.module('brewspberry').controller('BrewController', BrewController);

	BrewController.$inject = [ '$scope', 'BrewService', '$routeParams', 'TemperatureService'];

	function BrewController($scope, BrewService, $routeParams, TemperatureService) {

		var vm = this;
		vm.currentFullBrew = {};

		vm.showErrors = false;
		vm.showSuccess = false;
		vm.brewID = $routeParams.brewID;

		vm.chartOptions = {
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

		/**
		 * On page loaded, retrieves steps list and feeds view
		 */
		var init = function() {

			BrewService.getBrew(vm.brewID, function(response) {
				/**
				 * In case of success
				 */
				vm.currentFullBrew = response.data;
				vm.initCharts();
			}, function(response) {
				/**
				 * In case of error
				 */

				vm.showErrors = true;

				vm.submissionFailureMessage = "Code : " + response.statusCode
						+ ". Message : " + response.data;

			})


		}


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
			 */

			if (typeof(vm.currentFullBrew) != 'undefined'){

				var steps = vm.currentFullBrew.steps;
				var counter = 0;

				console.log(steps);
				for(var step in steps){
					// For each step, if step is active, initiates
					console.log(steps[step])
					if (steps[step].isActive){

						TemperatureService.initTemperaturesForStep(steps[step].id, function(response){

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
							vm.currentFullBrew.steps[step].chart = vm.getSeriesFromServiceList(response.data,
								vm.currentFullBrew.steps[step].beginning);

							}, function(response){

							vm.currentFullBrew.steps[step].chart = {data : [], series : [], labels : []};
								
							});
					}
					
					counter++;
					
				}
				
				
			}
			
		}
		
		
		
		/**
		 * Returns distinct list of probes to feed chart series
		 */
		vm.getSeriesFromServiceList = function(serviceObject, beginningOfStepTimestamp){
			/*
			 * TODO : be able to display multiple series with different timestamps
			 * 
			 */
			var result={series : [], data : [], labels : []};
			for (var obj in serviceObject){
				
				var serie = result.series.indexOf(serviceObject[obj].uuid);

				/*
				If serie does ot exist, create it and add values to
				 */
				if (serie == -1){
					
					result.series.push(serviceObject[obj].uuid);
					serie = result.series.indexOf(serviceObject[obj].uuid)
					result.data[serie] = [];
					result.labels[serie] = [];
				}
				//console.log ('serie2 : '+serie);

				result.data[serie].push(serviceObject[obj].value);
				var time = new Date((new Date(serviceObject[obj].date)).getTime() - beginningOfStepTimestamp);


				result.labels.push(time.getHours()+':'+time.getMinutes()+':'+time.getSeconds());
				
			}		
			
			return result;
		}

		angular.element(document).ready(init());

	}

})();