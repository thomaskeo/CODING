'use strict';

angular.module('codingApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('codingSession', {
                parent: 'entity',
                url: '/codingSession',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'codingApp.codingSession.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/codingSession/codingSessions.html',
                        controller: 'CodingSessionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('codingSession');
                        return $translate.refresh();
                    }]
                }
            })
            .state('codingSessionDetail', {
                parent: 'entity',
                url: '/codingSession/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'codingApp.codingSession.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/codingSession/codingSession-detail.html',
                        controller: 'CodingSessionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('codingSession');
                        return $translate.refresh();
                    }]
                }
            });
    });
