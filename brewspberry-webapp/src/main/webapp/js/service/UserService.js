/**
 * Created by xcaron on 08/09/2016.
 */




'use strict';

angular.module('brewspberry').factory('UserService', UserService);



UserService.$inject = ['$http'];


function UserService($http) {

    var UserService = {};

       UserService.getAllUsers = function () {
        var promise = $http({
            method: 'GET',
            url: CONSTANTS.USER_SERVICE_URL,

        }).then(function (response) {
            return response;
        }, function () {
            return response;
        });
        return promise;
    };
    UserService.getUser = function (id) {
            var promise = $http({
                method: 'GET',

                url: CONSTANTS.USER_SERVICE_URL + id,
            }).then(function (response) {
                return response;
            }, function () {
                return response;
            });
            return promise;
        };
    UserService.updateUser = function (id, obj) {
            var promise = $http({
                method: 'PUT',

                url: CONSTANTS.USER_SERVICE_URL + 'update/'+ id,
                data : obj
            }).then(function (response) {
                return response;
            }, function () {
                return response;
            });
            return promise;
        };
    UserService.createUser = function (obj) {
            var promise = $http({
                method: 'POST',

                url: CONSTANTS.USER_SERVICE_URL+'create',
                data : obj
            }).then(function (response) {
                return response;
            }, function () {
                return response;
            });
            return promise;
        };
    UserService.deleteUser = function (id) {
            var promise = $http({
                method: 'DELETE',
                url: CONSTANTS.USER_SERVICE_URL + 'delete/' + id,
            }).then(function (response) {
                return response;
            }, function () {
                return response;
            });
            return promise;
        };

        return UserService;
}