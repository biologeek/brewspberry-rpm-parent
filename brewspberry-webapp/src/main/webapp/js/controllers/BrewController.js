/**
 * Created by xcaron on 21/09/2016.
 */

(function() {

	'use strict';

	angular.module('brewspberry').controller('BrewController', BrewController);

	BrewController.$inject = [ '$scope', 'BrewService', '$routeParams', 'TemperatureService', '$interval'];

	function BrewController($scope, BrewService, $routeParams, TemperatureService, $interval) {

		var vm = this;
		vm.currentFullBrew = {};
		vm.currentFullBrew.steps = [{}];
		vm.currentFullBrew.steps[0].charts = [{data : [[20, 30]], series : ["1"], labels : [0,1]}];
		vm.data = [[20, 32, 10]];
		vm.labels=[1, 2, 3];
		vm.series = ['serie 1'];
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
			},
			animation : false
		}
		vm.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];

		/**
		 * On page loaded, retrieves steps list and feeds view
		 */
		var init = function() {
			console.log("0")

			BrewService.getBrew(vm.brewID, function(response) {
				/**
				 * In case of success
				 */
				console.log("1")

				vm.currentFullBrew = response.data;
				vm.currentFullBrew.steps[0].charts = [{data : [[]], series : [], labels : []}];

				vm.initCharts(function(){

					console.log("10")


				});
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




		vm.initCharts = function(callback){
			console.log("2")


			if (typeof(vm.currentFullBrew) != 'undefined'){

				var steps = vm.currentFullBrew.steps;
				var counter = 0;
				vm.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
				for(var step in steps){
					// For each step, if step is active, initiates
					if (steps[step].isActive){
						//vm.currentFullBrew.steps[step].charts = [{data : [[]], series : [], labels : []}];

						TemperatureService.initTemperaturesForStep(steps[step].id, function(response){
							console.log("IIIIIIIIIIIIIIIIINIT")

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
							 * 			brew : 1,
							 * 			chart : [
							 * 				{
							 *					data : [data...],
							  					labels : [labels...],
							  					series : [series...]
							  				},
							  				...
							  			]
							 * 		} , ...
							 * ]
							 *
							 */
							console.log("loop")
							vm.currentFullBrew.steps[step].charts = response.data;

							console.log("blou")
							}, function(response){

								vm.currentFullBrew.steps[step].charts = [{data : [[]], series : [], labels : []}];
								
							});
					}
					
					counter++;
					
				}

				console.log("3")

				callback();
				
			}
			
		}

/*
		$interval(function(){
			vm.currentFullBrew.steps[1].charts[0].data.push(Math.random()*50);

			var time = new Date((new Date()).getTime() - vm.currentFullBrew.steps[1].beginning);

			vm.currentFullBrew.steps[1].charts[0].labels.push(time.getHours()+':'+time.getMinutes()+':'+time.getSeconds());
		}, 5000);
*/
		//init();

	}

})();