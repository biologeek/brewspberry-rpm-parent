/**
 * Created by xcaron on 20/09/2016.
 */

(function(){
    'use strict';

    angular.module('brewspberry').controller('AccueilController', AccueilController);



AccueilController.$inject['$scope', 'BrewService'];

function AccueilController($scope, BrewService) {

    function getCurrentBrews (){

        BrewService.getAll(function(data){

            $scope.brewList = data;
        }, function (data) {

            $scope.brewList = {};
            $scope.showErrors = true;
            $scope.submissionFailureMessage = 'Failed to get brews. Error : '+ data.statusText + '. '+data.data;
        }
        );

    }
}
})();