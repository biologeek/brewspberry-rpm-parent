/**
 * Created by xcaron on 08/09/2016.
 */




'use strict';

angular.module('brewspberry').factory('UserService', UserService);



UserService.$inject = ['$http', 'CONSTANTS'];


function UserService($http, CONSTANTS) {

    var UserService = {};

       UserService.getAllUsers = function (callBackSuccess, callbackError) {
        var promise = $http({
            method: 'GET',
            url: CONSTANTS.USER_SERVICE_URL,

        }).then(function (response) {
            callBackSuccess(response);
        }, function (response) {
            return response;
        });
        return promise;
    };
    UserService.getUser = function (id, callBackSuccess, callbackError) {
            var promise = $http({
                method: 'GET',

                url: CONSTANTS.USER_SERVICE_URL + '/'+id,
            }).then(function (response) {
                callBackSuccess(response);
            }, function (response) {
                callbackError(response);
            });
            return promise;
        };
    UserService.updateUser = function (id, obj, callBackSuccess, callbackError) {
            var promise = $http({
                method: 'PUT',

                url: CONSTANTS.USER_SERVICE_URL + '/update/'+ id,
                data : obj
            }).then(function (response) {
                callBackSuccess(response);
            }, function (response) {
                callbackError(response);
            });
            return promise;
        };
    UserService.createUser = function (obj, callBackSuccess, callbackError) {
            var promise = $http({
                method: 'POST',

                url: CONSTANTS.USER_SERVICE_URL+'/create',
                data : obj
            }).then(function (response) {
                callBackSuccess(response);
            }, function (response) {
                callbackError(response);
            });
            return promise;
        };
    UserService.deleteUser = function (id, callBackSuccess, callbackError) {
            var promise = $http({
                method: 'DELETE',
                url: CONSTANTS.USER_SERVICE_URL + '/delete/' + id,
            }).then(function (response) {
                callBackSuccess(response);
            }, function (response) {
                callbackError(response);
            });
            return promise;
        };

        return UserService;
}