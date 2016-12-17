/**
 * Created by xcaron on 21/09/2016.
 */

(function() {

	'use strict';

	angular.module('brewspberry').controller('RealtimeController',
			RealtimeController);

	RealtimeController.$inject = [ '$scope', 'moment', 'BrewService',
			'$routeParams', 'TemperatureService', '$interval', 'CONSTANTS',
			'StepService', 'ActionnerService', 'Notification' ];

	function RealtimeController($scope, moment, BrewService, $routeParams,
			TemperatureService, $interval, CONSTANTS, StepService,
			ActionnerService, Notification) {
		var vm = this;
		/* This var records which index every device ID has in table */
		var positionFinder = [];

		vm.charts = []
		console.log("here")

		console.log("here")
		TemperatureService.getTemperaturesForActiveActionners(
				function(response) {
					processData(response.data, false);
				}, function(response) {
					Notification.error('Error ' + response.status + ' : '
							+ response.data);
				});

		var processData = function(rawData, isUpdate) {
			/*
			 * Receiving potentially several charts
			 */
			console.log("here")
			if (!isUpdate) {
				_.each(rawData, function(chart) {
					momentizeLabels(chart.concretes[0].labels,
							function(result) {
								chart.concretes[0].labels = result
							});
					vm.charts.push({
						"uuid" : chart.concretes[0].series[0],
						"chart" : chart.concretes[0]
					});
					
					limitDataSizeOnChart(chart.concretes[0])

				});
				isUpdate = true;
			} else {
				_.each(rawData,
					function(actioner) {
					var currentChart = _.find(
									vm.charts,
									function(chart) {
										return chart.concretes[0].series[0] == actioner.concretes[0].series[0];
									});
					currentChart.chart.concretes[0].data
							.push(actioner.data);

					limitDataSizeOnChart(actionner.concretes[0])
					var momentizedLabels = momentizeLabels(
							actioner.concretes[0].labels,
							function(result) {
								currentChart.chart.concretes[0].labels
										.push(result);
							});
				});
			}

		}

		var momentizeLabels = function(timestampLabels, callback) {
			var momentLabels = [];

			_.each(timestampLabels, function(timestampLabel) {
				var date = new Date(parseInt(timestampLabel));
				// console.log(date.getHours()+':'+date.getMinutes()+':'+date.getSeconds());
				//				
				// console.log(moment(date));

				if (!moment(date).isSame(moment(), 'day')) {
					momentLabels
							.push(date.getDay() + '/' + date.getMonth() + '/'
									+ date.getYear() + ' ' + date.getHours()
									+ ':' + date.getMinutes() + ':'
									+ date.getSeconds());
				} else {
					momentLabels.push(date.getHours() + ':' + date.getMinutes()
							+ ':' + date.getSeconds());
				}
			});
			callback(momentLabels)
		}

		/**
		 * If dataset is too large, removes first points to maximum dataset size
		 * 
		 * @param step
		 *            step counter
		 * @param actionner
		 *            actionner counter
		 */
		var limitDataSizeOnChart = function(chart) {

			if (chart.data && chart.data.length > 0) {
				if (chart.data.length > CONSTANTS.CHART_MAX_DATA_SIZE) {
					do {
						chart.data.shift();
						chart.labels.shift();
					} while (chart.data.length > CONSTANTS.CHART_MAX_DATA_SIZE)
				}
			}
		}
	}
})();
