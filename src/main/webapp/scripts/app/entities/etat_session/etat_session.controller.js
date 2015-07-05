'use strict';

angular.module('codingApp')
    .controller('Etat_sessionController', function ($scope, Etat_session) {
        $scope.etat_sessions = [];
        $scope.loadAll = function() {
            Etat_session.query(function(result) {
               $scope.etat_sessions = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Etat_session.update($scope.etat_session,
                function () {
                    $scope.loadAll();
                    $('#saveEtat_sessionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Etat_session.get({id: id}, function(result) {
                $scope.etat_session = result;
                $('#saveEtat_sessionModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Etat_session.get({id: id}, function(result) {
                $scope.etat_session = result;
                $('#deleteEtat_sessionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Etat_session.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEtat_sessionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.etat_session = {libelle: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
