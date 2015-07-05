'use strict';

angular.module('codingApp')
    .controller('CodingSessionDetailController', function ($scope, $stateParams, CodingSession, User, Etat_session) {
        $scope.codingSession = {};
        $scope.load = function (id) {
            CodingSession.get({id: id}, function(result) {
              $scope.codingSession = result;
            });
        };
        $scope.load($stateParams.id);
    });
