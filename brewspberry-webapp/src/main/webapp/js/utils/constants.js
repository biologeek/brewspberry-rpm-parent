/**
 * Created by xcaron on 15/09/2016.
 */


'use strict';

var app = angular.module('brewspberry');


var base_url = 'http://localhost:8080/'
app.constant('CONSTANTS', {
    'BACK_OFFICE_URL' : base_url,
    'USER_SERVICE_URL' : base_url+'brewspberry-api/userService',
    'PRODUCT_SERVICE_URL' : base_url+'brewspberry-api/productService',
    'BREW_SERVICE_URL' : base_url+'brewspberry-api/brewService',
    'STEP_SERVICE_URL' : base_url+'brewspberry-api/stepService',
    'TEMP_SERVICE_URL' : base_url+'brewspberry-api/temperatureService',
    'DEFAULT_CHART_RELOAD' : 5000,
    'CHART_MAX_DATA_SIZE' : 30,
    'DEFAULT_RANGE_MINUTES' : 10,
    'ACTIONNER_PICTURES' : {
    	"1" : {"on" : "images/thermo-on.png", "off" : "images/thermo-off.png"},
    	"2" : {"on" : "images/engine-on.png", "off" : "images/engine-off.png"},
    	"3" : {"on" : "images/pump-on.png", "off" : "images/pump-off.png"},
    	
    },
    'STEP_TYPES' : [
                    'CONCASSAGE', 
    				'PALIER',
    				'FILTRATION',
    				'EBULLITION',
    				'WHIRLPOOL',
    				'REFROIDISSEMENT',
    				'FERMENTATION',
    				'GARDE',
    				'EMBOUTEILLAGE',
    				'REFERMENTATION'
                         ],

    'STAGE_TYPES' : [
                'PROTEINIQUE', 
				'SACCHARIFICATION',
				'ALE',
				'DEXTRINES',
				'MASH_OUT'
                     ]
});