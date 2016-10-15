/**
 * Created by xcaron on 13/09/2016.
 */

(function() {
	'use strict';

	var app = angular.module('brewspberry');

	app.constant('URL_SERVICE_BASE', 'http://localhost:8080/brewspberry-api/')
			.constant('INGREDIENT_SERVICE_URL',
					URL_SERVICE_BASE + 'ingredientService/').constant(
					'USER_SERVICE_URL', URL_SERVICE_BASE + 'userService/')
			.constant('STEP_SERVICE_URL', URL_SERVICE_BASE + 'stepService/')
			.constant('TEMPERATURE_SERVICE_URL',
					URL_SERVICE_BASE + 'temperatureService/');

})();