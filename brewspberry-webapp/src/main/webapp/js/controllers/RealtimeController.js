/**
 * Created by xcaron on 21/09/2016.
 */

(function() {

	'use strict';

	angular.module('brewspberry').controller('RealtimeController', RealtimeController);

	RealtimeController.$inject = [ '$scope', 'BrewService', '$routeParams', 'TemperatureService', '$interval', 'CONSTANTS', 'StepService', 'ActionnerService', 'Notification'];

	function RealtimeController($scope, BrewService, $routeParams, TemperatureService, $interval, CONSTANTS, StepService, ActionnerService, Notification) {
		
		/* This var records which index every device ID has in table */
		var positionFinder = [];
		
		vm.charts = []
		
		var init = function(){
			TemperatureService.getTemperaturesForActiveActionners(function(response){
				processData(response.data, false);
			}, function(response){
				Notification.error('Error '+response.status+' : '+response.data);
			});
		}
		
		
		
		var processData = function(rawData, isUpdate){
			/*
			 * Receiving potentially several charts
			 */
			
			if (!isUpdate){
				for (var actioner in rawData){
					vm.charts.push({"uuid": actioner.series[0], "chart": actioner});
				}
			} else {
				for (var actioner in rawData){
					var currentChart = _.find(vm.charts, function(chart){ return chart.series[0] == actioner.series[0];});
					currentChart.chart.data.push(actioner.data);
					currentChart.chart.labels.push(actioner.labels);
				}
			}
			
		}
	}
})();
