/**
 * Created by xcaron on 20/09/2016.
 */

(function(){
    'use strict';

    angular.module('brewspberry').controller('AccueilController', AccueilController);



AccueilController.$inject=['$scope', 'BrewService', '$location'];

function AccueilController($scope, BrewService, $location) {


    var vm = this;

    vm.showSuccess = false;
    vm.showErrors = false;

    
    
    vm.goToCreation = function(){
    	$location.url('/creation/brew');    	
    }
    var init = function (){

        console.log("ini");
        BrewService.getAll(function(response){

            vm.brewList = response.data;
        }, function (data) {

            vm.brewList = {};
            vm.showErrors = true;
            vm.submissionFailureMessage = 'Failed to get brews. Error : '+ data.statusText + '. '+data.data;
        }
        );
    };

    angular.element(document).ready(init());
}
})();