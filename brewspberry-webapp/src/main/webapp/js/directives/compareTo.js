/**
 * Created by xcaron on 09/09/2016.
 */



angular.module('brewspberry').directive('brewMatch', brewMatch);


brewMatch.$inject = ['$parse'];


function brewMatch($parse){

    var directiveDefinition = {
        restrict : 'A',
        require : 'ngModel',
        scope : {otherModelValue : '=brewMatch'},
        link : link
    }




    return directiveDefinition;


    function link(scope, element, attrs, ngModel){

            ngModel.$validators.brewMatch = function(viewValue){

                return viewValue == scope.otherModelValue;

            };

            scope.$watch("otherModelValue", function(){
                ngModel.$validate();
            }
            );

    }

}