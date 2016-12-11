/**
 * Created by xcaron on 21/09/2016.
 */

(function() {

	'use strict';

	angular.module('brewspberry').controller('RealtimeController', RealtimeController);

	RealtimeController.$inject = [ '$scope', 'BrewService', '$routeParams', 'TemperatureService', '$interval', 'CONSTANTS', 'StepService', 'ActionnerService', 'Notification'];

	function RealtimeController($scope, BrewService, $routeParams, TemperatureService, $interval, CONSTANTS, StepService, ActionnerService, Notification) {
		var vm = this;
		/* This var records which index every device ID has in table */
		var positionFinder = [];
		
		vm.charts = []
		console.log("here")

		
		console.log("here")
		TemperatureService.getTemperaturesForActiveActionners(function(response){
			processData(response.data, false);
		}, function(response){
			Notification.error('Error '+response.status+' : '+response.data);
		});
		
		
		
		
		var processData = function(rawData, isUpdate){
			/*
			 * Receiving potentially several charts
			 */
			console.log("here")
			if (!isUpdate){
				_.each(rawData, function(actioner){
					vm.charts.push({"uuid": actioner.concretes[0].series[0], "chart": actioner.concretes[0]});
				});
			} else {
				_.each(rawData, function(actioner){
					var currentChart = _.find(vm.charts, function(chart){ return chart.series[0] == actioner.series[0];});
					currentChart.chart.data.push(actioner.data);
					currentChart.chart.labels.push(actioner.labels);
				});
			}
			
		}
	}
})();
