/**
 * Created by xcaron on 08/09/2016.
 */





angular.module('brewspberry').factory('UserService', UserService);



UserService.$inject = ['$http'];


function UserService($http){


    return
        {


            //TODO
            getAllUsers : function (){
                var promise = $http({
                    method : 'GET',
                    url : CONSTANTS.USER_SERVICE_URL,

                }).then(function(){

                },function(){

                });
            },
            getUser : {
                method: 'GET'
            },
            updateUser : {

                method : 'PUT'
            },
            createUser : {
                method : 'POST'
            },
            deleteUser :  {
                method : 'DELETE'
            }
        });


}