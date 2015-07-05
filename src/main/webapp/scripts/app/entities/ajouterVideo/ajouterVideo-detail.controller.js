'use strict';

angular.module('codingApp')
    .controller('AjouterVideoDetailController', function ($scope, $stateParams, AjouterVideo) {
        $scope.ajouterVideo = {};
        $scope.load = function (id) {
            AjouterVideo.get({id: id}, function(result) {
              $scope.ajouterVideo = result;
            });
        };
        $scope.load($stateParams.id);
    });
