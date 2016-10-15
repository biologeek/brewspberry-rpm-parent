/**
 * Created by xcaron on 21/09/2016.
 */

(function() {

	'use strict';

	angular.module('brewspberry').controller('BrewController', BrewController);

	BrewController.$inject = [ '$scope', 'BrewService', '$routeParams', 'TemperatureService', '$interval', 'CONSTANTS', 'StepService'];

	function BrewController($scope, BrewService, $routeParams, TemperatureService, $interval, CONSTANTS, StepService) {

		var vm = this;
		vm.currentFullBrew = {};
		vm.currentFullBrew.steps = [{}];


		vm.updateDelay = vm.updateDelay || CONSTANTS.DEFAULT_CHART_RELOAD;
		vm.defaultmaxPtsValue = vm.defaultmaxPtsValue || CONSTANTS.CHART_MAX_DATA_SIZE;
		vm.defaultRangeInMinutes = vm.defaultRangeInMinutes || CONSTANTS.DEFAULT_RANGE_MINUTES;
		vm.showErrors = false;
		vm.showSuccess = false;
		vm.brewID = $routeParams.brewID;

		vm.chartOptions = {
			scales: {
				yAxes: [
					{
						id: 'yAxis',
						type: 'linear',
						display: true,
						position: 'left'
					}
				]
			},
			legend: {
				display: true,
				labels: {
					fontSize: 10
				}
			},
			animation: false
		}

		/**
		 * On page loaded, retrieves steps list and feeds view
		 */
		var init = function () {

			BrewService.getBrew(vm.brewID, true, function (response) {
				/**
				 * In case of success
				 */

				vm.currentFullBrew = response.data;

				vm.initCharts(function () {


				});
			}, function (response) {
				/**
				 * In case of error
				 */

				vm.showErrors = true;

				vm.submissionFailureMessage = "Code : " + response.statusCode
					+ ". Message : " + response.data;

			})


		}

		/**
		 * Initiates data for charts
		 * @param callback
		 */
		vm.initCharts = function (callback) {


			if (typeof(vm.currentFullBrew) != 'undefined') {

				var steps = vm.currentFullBrew.steps;
				var counter = 0;


				for (let step in steps) {
					console.log(step+' STEP '+steps.length);


					// For each step, if step is active, initiates
					//if (steps[step].isActive) {
						//vm.currentFullBrew.steps[step].charts = [{data : [[]], series : [], labels : []}];
						console.log('STEeP '+step);
						console.log('STEeeeeP '+steps[step].id);

								TemperatureService.initTemperaturesForStep(steps[step].id, function(response){

									console.log("TemperatureService.initTemperaturesForStep("+step+", function (response) {")

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
									 * 		} , ...
									 * ]
									 *
									 */

									console.log('STuuuP '+step);

									processRawDataAndFeedActionners(response.data, step);
								}, function (response) {
									console.log('STaaaaP '+step);

									vm.currentFullBrew.steps[step].chart = [{data: [[]], series: [], labels: []}];

								});
							}
//					}


				}


				callback();



		}


		/**
		 * Updates charts data
		 */
		vm.updateCharts = function () {

			for (let step in vm.currentFullBrew.steps) {

				if (vm.currentFullBrew.steps[step].isActive) {
						TemperatureService.updateTemperaturesForStep(vm.currentFullBrew.steps[step].id, function (response) {

						processRawDataAndFeedActionners(response.data, step);

					}, function (response) {
							return ;
					});

				}

			}

		}


		/**
		 * From raw webservice data, appends temperture measurements to brew object
		 * and cuts excedent data
		 * @param rawData data from WS
		 * @param stepCounter step number in array
		 */
		var processRawDataAndFeedActionners = function (rawData, stepCounter) {


			if (rawData.length > 0) {
				/* for each temperature received */
				for (var e in rawData) {

					/* find corresponding actionner */
					var i = 0;
					for (var act in vm.currentFullBrew.steps[stepCounter].actioners) {
						if (typeof vm.currentFullBrew.steps[stepCounter].actioners[act].chart == "undefined" && vm.currentFullBrew.steps[stepCounter].actioners[act].type == 1){

							vm.currentFullBrew.steps[stepCounter].actioners[act].chart = {data : [[]], labels : [], series : []};

						}

						if (vm.currentFullBrew.steps[stepCounter].actioners[act].id == rawData[e].actionner || vm.currentFullBrew.steps[stepCounter].actioners[act].uuid == rawData[e].uuid) {

							/*add temperature to actionner chart*/
							if (vm.currentFullBrew.steps[stepCounter].actioners[act].type == 1) {
								if (typeof vm.currentFullBrew.steps[stepCounter].actioners[act].chart.data == "undefined") {

									vm.currentFullBrew.steps[stepCounter].actioners[act].chart.data = [[]];
									vm.currentFullBrew.steps[stepCounter].actioners[act].chart.label = [];
									vm.currentFullBrew.steps[stepCounter].actioners[act].chart.series = [vm.currentFullBrew.steps[stepCounter].actioners[act].uuid];
								}
								vm.currentFullBrew.steps[stepCounter].actioners[act].chart.data[0].push(rawData[e].value);
								vm.currentFullBrew.steps[stepCounter].actioners[act].chart.labels.push(formatDateForChartDisplay(rawData[e].date, stepCounter));
								vm.currentFullBrew.steps[stepCounter].actioners[act].chart.series = [vm.currentFullBrew.steps[stepCounter].actioners[act].uuid];
							}
						}
						limitDataSizeOnChart(stepCounter, i);
						i++;
					}
					console.log (vm.currentFullBrew.steps)
				}


			}


		}

		/**
		 * If dataset is too large, removes first points to maximum dataset size
		 * @param step step counter
		 * @param actionner actionner counter
		 */
		var limitDataSizeOnChart = function (step, actionner) {

			if (typeof vm.currentFullBrew.steps[step].actioners[actionner].chart != "undefined") {
				while (vm.currentFullBrew.steps[step].actioners[actionner].chart.data[0].length > CONSTANTS.CHART_MAX_DATA_SIZE) {
					vm.currentFullBrew.steps[step].actioners[actionner].chart.data[0].shift();
					vm.currentFullBrew.steps[step].actioners[actionner].chart.labels.shift();

				}
			}

		}


		/**
		 * Gets time difference between date and step beginning
		 * @param date
		 * @param stepCount
		 * @returns {Date}
		 */
		var formatDateForChartDisplay = function (date, stepCount) {


			var beginning = vm.currentFullBrew.steps[stepCount].beginning;

			var result = new Date(date - beginning);

			var stringResult = '';

			if (result.getHours() > 0) {
				stringResult = result.getHours() + ':';
			}

			stringResult += result.getMinutes() + ':' + result.getSeconds();

			return stringResult;

		}


		init();

/*

		$interval(function () {

			vm.updateCharts();

		}, vm.updateDelay);

*/
		/******************************************************************************************/
		/****************************   ACTIONNER ACTIVATION  *************************************/
		/******************************************************************************************/



		/**
		 *
		 *
		 */
		vm.changeActionnerState = function (actionerID, stepID, index) {

			if (vm.currentFullBrew.steps[stepID].actioners[index].state == 'ON') {
				TemperatureService
					.deactivate(
						actionerID,
						function (response) {
							/*
							 * In case of success, changing picture
							 */

							var actionnerType = vm.currentFullBrew.steps[stepID].actioners[actionerID].type;
							
							vm.currentFullBrew.steps[stepID].actioners[actionerID].picture = CONSTANTS.ACTIONNER_PICTURES[actionnerType].off;
							vm.currentFullBrew.steps[stepID].actioners[index].state == 'OFF'

						},
						function (response) {

							/*
							 * In case of error, display error message
							 */

							vm.showErrors = true;

							vm.submissionFailureMessage = "Could not deactivate. Error : "
								+ response.statusText
								+ ", "
								+ response.data;

						});

			} else if (vm.currentFullBrew.steps[stepID].actioners[index].state == 'OFF') {

				TemperatureService
					.activate(
						actionerID,
						function (response) {
							/*
							 * In case of success, changing picture to off picture
							 */
							var actionnerType = vm.currentFullBrew.steps[stepID].actioners[actionerID].type;
							
							vm.currentFullBrew.steps[stepID]
							.actioners[actionerID].picture = CONSTANTS.ACTIONNER_PICTURES[actionnerType].off;
							vm.currentFullBrew.steps[stepID].actioners[index].state == 'ON'
						},
						function (response) {

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


		/******************************************************************************************/
		/****************************   ADDING A STEP BLOCK   *************************************/
		/******************************************************************************************/

		/**
		 *
		 *
		 */
		vm.addAStep = function (addAStepForm) {


			var addedStep = addAStepForm.step;
			
			console.log(addedStep);

			// Service call
			StepService.add(vm.currentFullBrew.id, addedStep, function (response) {

				vm.showSuccess = true;
				vm.showErrors = false;


				vm.submissionSuccessMessage = "Step added to brew";


				//Adding step to current steps list
				vm.currentFullBrew.steps.push(response.data);

			}, function (response) {

				vm.showSuccess = false;
				vm.showErrors = true;


				vm.submissionSuccessMessage = "Step could not be added : " + response.statusText + ", " + response.data;


			});


		}
	}

})();