/**
 * Created by xcaron on 08/09/2016.
 */



(function () {
'use strict';

angular.module('brewspberry').factory('UtilsService', UtilsService);



UtilsService.$inject = ['$http', 'CONSTANTS'];


function UtilsService($http, CONSTANTS) {

    var UtilsService = {};

       UtilsService.getAllUnits = function (callBackSuccess, callbackError) {
        var promise = $http({
            method: 'GET',
            url: CONSTANTS.UTILS_SERVICE_URL+'/units',

        }).then(function (response) {
            callBackSuccess(response);
        }, function (response) {
            callbackError(response);
        });
        return promise;
    };
        return UtilsService;
}


})();