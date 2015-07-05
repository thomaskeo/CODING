'use strict';

angular.module('codingApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('etat_session', {
                parent: 'entity',
                url: '/etat_session',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'codingApp.etat_session.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/etat_session/etat_sessions.html',
                        controller: 'Etat_sessionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('etat_session');
                        return $translate.refresh();
                    }]
                }
            })
            .state('etat_sessionDetail', {
                parent: 'entity',
                url: '/etat_session/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'codingApp.etat_session.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/etat_session/etat_session-detail.html',
                        controller: 'Etat_sessionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('etat_session');
                        return $translate.refresh();
                    }]
                }
            });
    });
