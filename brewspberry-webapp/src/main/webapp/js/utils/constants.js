/**
 * Created by xcaron on 15/09/2016.
 */

'use strict';

var app = angular.module('brewspberry');

var base_url = 'http://127.0.0.1:10080/'
var root_path = 'brewspberry-api'
app.constant('CONSTANTS', {

	'BACK_OFFICE_URL' : base_url,
	'USER_SERVICE_URL' : base_url + root_path + '/userService',
	'PRODUCT_SERVICE_URL' : base_url + root_path + '/productService',
	'BREW_SERVICE_URL' : base_url + root_path + '/brewService',
	'STEP_SERVICE_URL' : base_url + root_path + '/stepService',
	'TEMP_SERVICE_URL' : base_url + root_path + '/temperatureService',
	'ACTIONNER_SERVICE_URL' : base_url + root_path + '/actionner',
	'INGREDIENT_SERVICE_URL' : base_url + root_path + '/ingredient',
	'UTILS_SERVICE_URL' : base_url + root_path + '/utils',
	'DEFAULT_CHART_RELOAD' : 5000,
	'CHART_MAX_DATA_SIZE' : 30,
	'DEFAULT_RANGE_MINUTES' : 10,
	'ACTIONNER_PICTURES' : {
		"DS18B20" : {
			"on" : "images/thermo-on.jpg",
			"off" : "images/thermo-off.jpg"
		},
		"ENGINE_RELAY" : {
			"on" : "images/engine-on.png",
			"off" : "images/engine-off.png"
		},
		"PUMP_RELAY" : {
			"on" : "images/pump-on.jpg",
			"off" : "images/pump-off.jpg"
		},

	},
	'STEP_TYPES' : [ 'CONCASSAGE', 'PALIER', 'FILTRATION', 'EBULLITION',
			'WHIRLPOOL', 'REFROIDISSEMENT', 'FERMENTATION', 'GARDE',
			'EMBOUTEILLAGE', 'REFERMENTATION' ],

	'STAGE_TYPES' : [ 'PROTEINIQUE', 'SACCHARIFICATION', 'ALE', 'DEXTRINES',
			'MASH_OUT' ],
	'BREW_STATUSES' : [ 'NOT_STARTED', 'COMPLETE', 'BREWING', 'FERMENTING',
			'MATURING', 'BOTTLED', 'DRINKABLE', 'FINISHED' ],
	'DURATIONS' : [ {
		"obj" : "13",
		"trad" : "seconds"
	}, {
		"obj" : "12",
		"trad" : "minutes"
	}, {
		"obj" : "10",
		"trad" : "hours"
	}, {
		"obj" : "5",
		"trad" : "days"
	}, {
		"obj" : "2",
		"trad" : "months"
	}, {
		"obj" : "1",
		"trad" : "years"
	}, ],

	'FLOCULATION_LEVELS' : [ {
		"enum" : "HIGH",
		"trad" : "high"
	}, {
		"enum" : "MEDIUM",
		"trad" : "medium"
	}, {
		"enum" : "LOW",
		"trad" : "low"
	} ],
	'ING_TYPES' : [ {
		"enum" : "MALT",
		"trad" : "Malt"
	}, {
		"enum" : "HOP",
		"trad" : "Hop"
	}, {
		"enum" : "YEAST",
		"trad" : "yeast"
	} ],
	'HOP_TYPES' : [ {
		"enum" : "BITTERING",
		"trad" : "Bittering"
	}, {
		"enum" : "AROMATIC",
		"trad" : "Aromatic"
	}, {
		"enum" : "BOTH",
		"trad" : "Both"
	} ]

});