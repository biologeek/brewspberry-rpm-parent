/**
 * Created by xcaron on 09/09/2016.
 */

(function(){
'use strict';

var app = angular.module('brewspberry', [
    'ngMessages',
    'ngRoute',
    'ngMaterial',
    'chart.js',
    'mdPickers',
    'ui-notification'
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
        }).when('/chart/:brewID/:stepID/:actID', {
            templateUrl : 'chart.html',
            controller : 'BrewController'
        }).when('/create/brew/', {
        	templateUrl : 'create.brew.html',
        	controller : 'BrewCreationController'
        }).when('/actionners', {
        	templateUrl : 'actionners.html',
        	controller : 'ActionnerController'
        }).when('/ingredients', {
        	templateUrl : 'ingredients.html',
        	controller : 'IngredientController'
        }).when('/realtime', {
        	templateUrl : 'realtime.html',
        	controller : 'BrewController'
        })
        .otherwise( {redirectTo : '/accueil'});
    });
    
    
    app.config(function(NotificationProvider) {
        NotificationProvider.setOptions({
            delay: 5000,
            startTop: 20,
            startRight: 10,
            verticalSpacing: 20,
            horizontalSpacing: 20,
            positionX: 'right',
            positionY: 'bottom'
        });
    });
})();