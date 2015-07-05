'use strict';

angular.module('codingApp')
    .controller('Etat_sessionDetailController', function ($scope, $stateParams, Etat_session) {
        $scope.etat_session = {};
        $scope.load = function (id) {
            Etat_session.get({id: id}, function(result) {
              $scope.etat_session = result;
            });
        };
        $scope.load($stateParams.id);
    });
