/**
 * Created by xcaron on 09/09/2016.
 */

(function(){
'use strict';

var app = angular.module('brewspberry', [
    'ngMessages',
    'ngRoute',
    'ngMaterial'
]);

    app.config(function($routeProvider){


        $routeProvider
        .when('/accueil', {
            templateUrl : 'accueil.html',
            controller : 'AccueilController'
        })
        .when('/brew/:brewID', {
            templateUrl : 'brew.html',
            controller : 'BrewController'
        })
        .otherwise( {redirectTo : '/accueil'});
    });
})();