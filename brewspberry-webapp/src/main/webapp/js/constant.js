/**
 * Created by xcaron on 13/09/2016.
 */



(function(){
    'use strict';


    var app = angular.module('brewspberry');

    app.constant('URL_SERVICE_BASE', 'http://192.168.0.20:8080/brewspberry-api/')
        .constant('INGREDIENT_SERVICE_URL', URL_SERVICE_BASE+'ingredientService/')
        .constant('USER_SERVICE_URL', URL_SERVICE_BASE+'userService/');


})();