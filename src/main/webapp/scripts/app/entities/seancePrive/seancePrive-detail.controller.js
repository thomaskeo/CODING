'use strict';

angular.module('codingApp')
    .controller('SeancePriveDetailController', function ($scope, $stateParams, SeancePrive, User) {
        $scope.seancePrive = {};
        $scope.load = function (id) {
            SeancePrive.get({id: id}, function(result) {
              $scope.seancePrive = result;
            });
        };
        $scope.load($stateParams.id);
    });
