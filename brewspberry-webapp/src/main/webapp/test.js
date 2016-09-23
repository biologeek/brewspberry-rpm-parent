/**
 * Created by xcaron on 22/09/2016.
 */



angular.module('test', ['ngRoute']).config(

    function($routeProvider){


        $routeProvider
            .when('/accueil', {
                templateUrl : 'test/page1.html'
            })
            .when('/page2', {
                templateUrl : 'test/page2.html'
            })
            .otherwise( {redirectTo : '/accueil'});
    }

);