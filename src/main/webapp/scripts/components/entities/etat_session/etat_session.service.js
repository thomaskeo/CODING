'use strict';

angular.module('codingApp')
    .factory('Etat_session', function ($resource) {
        return $resource('api/etat_sessions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
