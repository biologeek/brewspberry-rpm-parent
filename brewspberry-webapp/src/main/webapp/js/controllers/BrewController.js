/**
 * Created by xcaron on 21/09/2016.
 */


(function () {

    'use strict';


    angular.module('brewspberry').controller('BrewController', BrewController);


    BrewController.$inject=['$scope', 'BrewService', '$routeParams'];

    function BrewController($scope, BrewController, $routeParams){


        var brewID = $routeParams.brewID

    }

})();