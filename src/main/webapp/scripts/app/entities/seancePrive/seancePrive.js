'use strict';

angular.module('codingApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('seancePrive', {
                parent: 'entity',
                url: '/seancePrive',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'codingApp.seancePrive.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/seancePrive/seancePrives.html',
                        controller: 'SeancePriveController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('seancePrive');
                        return $translate.refresh();
                    }]
                }
            })
            .state('seancePriveDetail', {
                parent: 'entity',
                url: '/seancePrive/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'codingApp.seancePrive.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/seancePrive/seancePrive-detail.html',
                        controller: 'SeancePriveDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('seancePrive');
                        return $translate.refresh();
                    }]
                }
            });
    });
