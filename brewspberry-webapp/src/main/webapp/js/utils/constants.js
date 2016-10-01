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
    'TEMP_SERVICE_URL' : base_url+'brewspberry-api/temperatureService',
    'DEFAULT_CHART_RELOAD' : 1000,
    'CHART_MAX_DATA_SIZE' : 30
});