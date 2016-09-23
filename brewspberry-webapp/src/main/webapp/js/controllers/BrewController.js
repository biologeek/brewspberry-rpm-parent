/**
 * Created by xcaron on 21/09/2016.
 */


(function () {

    'use strict';


    angular.module('brewspberry').controller('BrewController', BrewController);


    BrewController.$inject=['$scope', 'BrewService', '$routeParams'];

    function BrewController($scope, BrewService, $routeParams) {

        vm.currentFullBrew = {};
        var vm = this;

        vm.showErrors = false;
        vm.showSuccess = false;
        var brewID = $routeParams.brewID;

        /**
         * On page loaded, retrieves steps list and feeds view
         */
        vm.$on('$viewContentLoaded', function () {

            BrewService.getFullBrew(brewID, function (response) {
                /**
                 * In case of success
                 */
                vm.currentFullBrew = response.data;

            }, function (response) {
                /**
                 * In case of error
                 */

                vm.showErrors = true;

                vm.submissionFailureMessage = "Code : "+response.statusCode+". Message : "+response.data;


            })

        });

    }

})();