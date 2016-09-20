/**
 * Created by xcaron on 20/09/2016.
 */

(function () {

'use strict';

angular.module('brewspberry').factory('BrewService', BrewService);



    BrewService.$inject = ['$http', 'CONSTANTS'];

function BrewService($http, CONSTANTS) {

    var BrewService = {};

    BrewService.getAll = function (callBackSuccess, callbackError) {
        var promise = $http({
            method: 'GET',
            url: CONSTANTS.BREW_SERVICE_URL,

        }).then(function (response) {
            callBackSuccess(response);
        }, function (response) {
            callbackError(response);
        });
        return promise;
    };
    BrewService.getBrew = function (id, callBackSuccess, callbackError) {
        var promise = $http({
            method: 'GET',

            url: CONSTANTS.BREW_SERVICE_URL + '/'+id,
        }).then(function (response) {
            callBackSuccess(response);
        }, function (response) {
            callbackError(response);
        });
        return promise;
    };
    BrewService.updateBrew = function (id, obj, callBackSuccess, callbackError) {
        var promise = $http({
            method: 'PUT',

            url: CONSTANTS.BREW_SERVICE_URL + '/update/'+ id,
            data : obj
        }).then(function (response) {
            callBackSuccess(response);
        }, function (response) {
            callbackError(response);
        });
        return promise;
    };
    BrewService.createBrew = function (obj, callBackSuccess, callbackError) {
        var promise = $http({
            method: 'POST',

            url: CONSTANTS.BREW_SERVICE_URL+'/create',
            data : obj
        }).then(function (response) {
            callBackSuccess(response);
        }, function (response) {
            callbackError(response);
        });
        return promise;
    };
    BrewService.deleteBrew = function (id, callBackSuccess, callbackError) {
        var promise = $http({
            method: 'DELETE',
            url: CONSTANTS.BREW_SERVICE_URL + '/delete/' + id,
        }).then(function (response) {
            callBackSuccess(response);
        }, function (response) {
            callbackError(response);
        });
        return promise;
    };

    return UserService;
}
})();