/**
 * Created by xcaron on 15/09/2016.
 */

'use strict';

var app = angular.module('brewspberry');

var base_url = 'http://localhost:8080/'
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
		"1" : {
			"on" : "images/thermo-on.png",
			"off" : "images/thermo-off.png"
		},
		"2" : {
			"on" : "images/engine-on.png",
			"off" : "images/engine-off.png"
		},
		"3" : {
			"on" : "images/pump-on.png",
			"off" : "images/pump-off.png"
		},

	},
	'STEP_TYPES' : [ 'CONCASSAGE', 'PALIER', 'FILTRATION', 'EBULLITION',
			'WHIRLPOOL', 'REFROIDISSEMENT', 'FERMENTATION', 'GARDE',
			'EMBOUTEILLAGE', 'REFERMENTATION' ],

	'STAGE_TYPES' : [ 'PROTEINIQUE', 'SACCHARIFICATION', 'ALE', 'DEXTRINES',
			'MASH_OUT' ],
	'BREW_STATUSES' : [ 'NOT_STARTED', 'COMPLETE', 'BREWING', 'FERMENTING',
			'MATURING', 'BOTTLED', 'DRINKABLE', 'FINISHED' ],
			
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