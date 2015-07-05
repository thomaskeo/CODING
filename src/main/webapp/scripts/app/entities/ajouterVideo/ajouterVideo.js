'use strict';

angular.module('codingApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ajouterVideo', {
                parent: 'entity',
                url: '/ajouterVideo',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'codingApp.ajouterVideo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ajouterVideo/ajouterVideos.html',
                        controller: 'AjouterVideoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ajouterVideo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('ajouterVideoDetail', {
                parent: 'entity',
                url: '/ajouterVideo/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'codingApp.ajouterVideo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ajouterVideo/ajouterVideo-detail.html',
                        controller: 'AjouterVideoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ajouterVideo');
                        return $translate.refresh();
                    }]
                }
            });
    });
