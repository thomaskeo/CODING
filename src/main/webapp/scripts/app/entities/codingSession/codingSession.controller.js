'use strict';

angular.module('codingApp')
    .controller('CodingSessionController', function ($scope, CodingSession, User, Etat_session, ParseLinks) {
        $scope.codingSessions = [];
        $scope.users = User.query();
        $scope.etat_sessions = Etat_session.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            CodingSession.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.codingSessions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            CodingSession.update($scope.codingSession,
                function () {
                    $scope.loadAll();
                    $('#saveCodingSessionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            CodingSession.get({id: id}, function(result) {
                $scope.codingSession = result;
                $('#saveCodingSessionModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            CodingSession.get({id: id}, function(result) {
                $scope.codingSession = result;
                $('#deleteCodingSessionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            CodingSession.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCodingSessionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.codingSession = {theme: null, sessionStart: null, sessionEnd: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
