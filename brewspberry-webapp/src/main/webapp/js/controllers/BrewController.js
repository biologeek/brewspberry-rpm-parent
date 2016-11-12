/**
 * Created by xcaron on 21/09/2016.
 */

(function() {

	'use strict';

	angular.module('brewspberry').controller('BrewController', BrewController);

	BrewController.$inject = [ '$scope', 'BrewService', '$routeParams', 'TemperatureService', '$interval', 'CONSTANTS', 'StepService', 'ActionnerService', 'Notification'];

	function BrewController($scope, BrewService, $routeParams, TemperatureService, $interval, CONSTANTS, StepService, ActionnerService, Notification) {

		var vm = this;
		vm.currentFullBrew = {};
		vm.currentFullBrew.steps = [{}];
		vm.lastIDs = {};

		vm.currentFullStep = {};

		vm.updateDelay = vm.updateDelay || CONSTANTS.DEFAULT_CHART_RELOAD;
		vm.defaultmaxPtsValue = vm.defaultmaxPtsValue || CONSTANTS.CHART_MAX_DATA_SIZE;
		vm.defaultRangeInMinutes = vm.defaultRangeInMinutes || CONSTANTS.DEFAULT_RANGE_MINUTES;
		vm.showErrors = false;
		vm.showSuccess = false;
		vm.brewID = $routeParams.brewID;
		vm.stepID = $routeParams.stepID; 
		vm.stageTypes=CONSTANTS.STAGE_TYPES;
		vm.stepTypes=CONSTANTS.STEP_TYPES;
		vm.availableActionners = [];
		
		
		
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
		
		

		
		init();


		 $interval(function () {
			var delay = vm.updateDelay
		 
			 vm.updateCharts(); 
		 
		 }, vm.updateDelay);
		 

		/**
		 * On page loaded, retrieves steps list and feeds view
		 */
		function init () {

			if (typeof vm.brewID != 'undefined'){
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
	
				});
			} else if (typeof vm.stepID != 'undefined'){
				
				StepService.getStep(vm.stepID, function (response) {
					/**
					 * In case of success
					 */
	
					vm.currentFullStep = response.data;
	
					vm.initCharts(function (){});
				}, function (response) {
					/**
					 * In case of error
					 */
	
					vm.showErrors = true;
	
					vm.submissionFailureMessage = "Code : " + response.statusCode
						+ ". Message : " + response.data;
	
				})
	
				
			}
			
			ActionnerService.getAvailableActionners(function(response){
				console.log(response.data);
				vm.availableActionners = response.data;
				
				console.log (response.data)
				
			}, function(response){
				
				vm.showErrors = true;

				vm.submissionFailureMessage = vm.submissionFailureMessage + "\nCould not retrieve actionners. Error : "
					+ response.statusText;			
				
			});
		}

		/**
		 * Initiates data for charts
		 * 
		 * @param callback
		 */
		vm.initCharts = function (callback) {


			// FIXME Pas ouf de passer par des variables globale, penser a les
			// injecter en paramètre
			if (typeof(vm.currentFullBrew) != 'undefined') {

				var steps = vm.currentFullBrew.steps;
				var counter = 0;


				for (let step in steps) {
					
					// For each step, if step is active, initiates
					// if (steps[step].isActive) {
						// vm.currentFullBrew.steps[step].charts = [{data :
						// [[]], series : [], labels : []}];
					initChartForStep(steps[step]);
				}


				callback();

			} else if (typeof vm.currentFullStep != 'undefined'){
				
				initChartForStep(vm.currentFullStep);				
				
			}

		}

			
		var initChartForStep = function (stepObj){
			for (let act in stepObj.actioners){
				
				if(stepObj.actioners[act].type == 1){
					TemperatureService.initTemperaturesForStep(stepObj.id, stepObj.actioners[act].uuid, function(response){


						/*
						 * Receiving object : [ { id : 1, value : 20.0, date :
						 * 123456789123456, //date in milliseconds uuid :
						 * '123456azerty456987', name : 'PROBE1', actionner : 3,
						 * step : 4, brew : 1, } , ... ]
						 * 
						 */

						
						vm.lastIDs[stepObj.actioners[act].uuid] = response.data.lastID;
						processRawDataAndFeedActionners(response.data, step, stepObj.actioners[act].uuid, false);
					}, function (response) {

						stepObj.chart = [{data: [[]], series: [], labels: []}];

					});
				}
			}
		}
	

		/**
		 * Updates charts data
		 */
		vm.updateCharts = function () {
			
			for (let step in vm.currentFullBrew.steps) {
				updateChartForStep(vm.currentFullBrew.steps[step]);				
			}

		}


		var updateChartForStep = function(stepObj){
			if (stepObj.active) {

				for (let act in stepObj.actioners) {
					
				
					TemperatureService.updateTemperaturesForStep(stepObj.id, 
							stepObj.actioners[act], 
						vm.lastIDs[stepObj.actioners[act].uuid], 
						function (response) {

							processRawDataAndFeedActionners(response.data, step, stepObj.actioners[act].uuid, true);

						}, function (response) {
								return ;
						}
					);
				}

			}

		}
		/**
		 * From raw webservice data, appends temperture measurements to brew
		 * object and cuts excedent data
		 * 
		 * @param rawData
		 *            data from WS
		 * @param stepCounter
		 *            step number in array
		 */
		var processRawDataAndFeedActionners = function (rawData, stepCounter, uuid, isUpdate) {
// TODO
			if (rawData.concretes[0] != undefined){
				if(!isUpdate){
					for(let act in vm.currentFullBrew.steps[stepCounter].actioners){
						if (vm.currentFullBrew.steps[stepCounter].actioners[act].uuid == uuid){
							vm.currentFullBrew.steps[stepCounter].actioners[act].chart = rawData.concretes;				
						}
					}
					
				} else {
					for(let act in vm.currentFullBrew.steps[stepCounter].actioners){
						if (vm.currentFullBrew.steps[stepCounter].actioners[act].uuid == uuid){
							
							vm.currentFullBrew.steps[stepCounter].actioners[act].chart[0].data.push(...rawData.concretes[0].data);
							vm.currentFullBrew.steps[stepCounter].actioners[act].chart[0].labels.push(...rawData.concretes[0].labels);
						}
					}
				}
				
				for(let act in vm.currentFullBrew.steps[stepCounter].actioners){
	
					limitDataSizeOnChart(stepCounter, act);
				}
			}
		}

		/**
		 * If dataset is too large, removes first points to maximum dataset size
		 * 
		 * @param step
		 *            step counter
		 * @param actionner
		 *            actionner counter
		 */
		var limitDataSizeOnChart = function (step, actionner) {

			if (typeof vm.currentFullBrew.steps[step].actioners[actionner].chart[0] != "undefined") {
				if (vm.currentFullBrew.steps[step].actioners[actionner].chart[0].data.length > CONSTANTS.CHART_MAX_DATA_SIZE) {
					do  {
					
						vm.currentFullBrew.steps[step].actioners[actionner].chart[0].data.shift();
						vm.currentFullBrew.steps[step].actioners[actionner].chart[0].labels.shift();
	
					} while(vm.currentFullBrew.steps[step].actioners[actionner].chart[0].data.length > CONSTANTS.CHART_MAX_DATA_SIZE)
				}
			}

		}


		/**
		 * Gets time difference between date and step beginning
		 * 
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


		

 		/** *************************************************************************************** */
		/**
		 * ******************************* STEP START/STOP
		 * ***************************************
		 */
		/** *************************************************************************************** */
		
		/**
		 * Calls service that will start or stop step and feed real beginning
		 * and end dates
		 */
		var toggleStepForReal = function(isStart, stepID){
			
			if (stepID >= 0){
				if (isStart){
					
					StepService.startStepForReal(stepID);
					
				} else {
					
					StepService.stopStepForReal(stepID);
					
				}
			}
			
		}
		
		
		
		
		/** *************************************************************************************** */
		/**
		 * ************************** ACTIONNER ACTIVATION
		 * ***************************************
		 */
		/** *************************************************************************************** */

	
		/**
		 * 
		 * 
		 */
		vm.changeActionnerState = function (actionerID, stepID, index) {
			console.log('changing'+actionerID+ ' '+stepID+' '+index)
			
			
			var currentAct = _.find(
					_.find(vm.currentFullBrew.steps, 
							function (elt){ 
								return elt.id == stepID ? elt : null
							}).actioners, 
							function (elt){ 
								return elt.id == actionerID ? elt : null
							});
			
			if (currentAct.status == 'STARTED') {
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

			} else if (currentAct.status == 'IDLE' || currentAct.status == 'PAUSED' || currentAct.status == 'STOPPED') {

				TemperatureService
					.activate(
						actionerID,
						function (response) {
							/*
							 * In case of success, changing picture to off
							 * picture
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

		
		
		/**
		 * Clicking 'Now' feeds date and time field
		 */
		vm.beginNow = function(field){
			
			vm.addedStep[field] = new Date();
			
		}

		/** *************************************************************************************** */
		/**
		 * ************************** ADDING A STEP BLOCK
		 * ************************************
		 */
		/** *************************************************************************************** */

		/**
		 * Adds a new step in database and in the interface
		 * 
		 */
		vm.addAStep = function (addAStepForm) {

			if (addAStepForm.$dirty){
				
				vm.createStep();
				
			}
		}
		
		vm.createStep = function(){
			
			vm.addedStep.endDate = vm.addedStep.endDateJs.getTime();
			vm.addedStep.beginningDate = vm.addedStep.beginningDateJs.getTime();
			
			vm.addedStep.brewID = vm.currentFullBrew.id;
			

			console.log(vm.addedStep);

			// Service call
			StepService.add(vm.currentFullBrew.id, vm.addedStep, function (response) {

				vm.showSuccess = true;
				vm.showErrors = false;


				vm.submissionSuccessMessage = "Step added to brew";


				// Adding step to current steps list
				vm.currentFullBrew.steps.push(response.data);

			}, function (response) {

				vm.showSuccess = false;
				vm.showErrors = true;


				vm.submissionSuccessMessage = "Step could not be added : " + response.statusText + ", " + response.data;


			});

		}
		
		vm.addActionerAndPop = function(actioner){
			
			if (typeof vm.addedStep.actioners == 'undefined'){
				vm.addedStep.actioners = [];
			}
			actioner.brewId = vm.currentFullBrew.id;
			vm.addedStep.actioners.push(actioner);
			
			Notification.success("Added "+actioner.uuid);
		}
	}

})();