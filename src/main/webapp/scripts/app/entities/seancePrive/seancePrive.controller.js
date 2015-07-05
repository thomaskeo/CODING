'use strict';

angular.module('codingApp')
    .controller('SeancePriveController', function ($scope, SeancePrive, User, ParseLinks) {
        $scope.seancePrives = [];
        $scope.users = User.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            SeancePrive.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.seancePrives = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            SeancePrive.update($scope.seancePrive,
                function () {
                    $scope.loadAll();
                    $('#saveSeancePriveModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            SeancePrive.get({id: id}, function(result) {
                $scope.seancePrive = result;
                $('#saveSeancePriveModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            SeancePrive.get({id: id}, function(result) {
                $scope.seancePrive = result;
                $('#deleteSeancePriveConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            SeancePrive.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSeancePriveConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.seancePrive = {titre: null, categorie: null, sujet: null, datedebut: null, datefin: null, nbutilisateur: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
