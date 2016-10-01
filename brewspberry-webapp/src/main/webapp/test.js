/**
 * Created by xcaron on 22/09/2016.
 */



angular.module('test', ['chart.js'])
    .controller('ChartController', function ($scope) {




        $scope.data = [[1,2,3], [3, 2, 1]];
        $scope.series = ['Serie', 'ires'];
        $scope.labels = ['a', 'b', 'c'];

        console.log(typeof $scope.labels)


    });