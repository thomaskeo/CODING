'use strict';

angular.module('codingApp')
    .controller('AjouterVideoController', function ($scope, AjouterVideo, ParseLinks) {
        $scope.ajouterVideos = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            AjouterVideo.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.ajouterVideos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            AjouterVideo.update($scope.ajouterVideo,
                function () {
                    $scope.loadAll();
                    $('#saveAjouterVideoModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            AjouterVideo.get({id: id}, function(result) {
                $scope.ajouterVideo = result;
                $('#saveAjouterVideoModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            AjouterVideo.get({id: id}, function(result) {
                $scope.ajouterVideo = result;
                $('#deleteAjouterVideoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AjouterVideo.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAjouterVideoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.ajouterVideo = {titre: null, categorie: null, sujet: null, url: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
