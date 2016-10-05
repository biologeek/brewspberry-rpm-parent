/**
 * 
 */



(function () {

'use strict';

angular.module('brewspberry').factory('StepService', StepService);

BrewService.$inject = ['$http', 'CONSTANTS'];

function StepService($http, CONSTANTS) {

    var StepServiceFactory = {};


    StepServiceFactory.add = function(brewID, step, callbackSuccess, callbackError){
    	var promise;
    	
    	if (brewID > 0 && step != {}){
    		    		
    		promise = $http({
                method: 'POST',
                url: CONSTANTS.STEP_SERVICE_URL+'/add',
                data : step

            }).then(function (response) {
                callBackSuccess(response);
            }, function (response) {
                callbackError(response);
            });

    		
    	}
    }
    	
    }
    
    return StepServiceFactory;

}