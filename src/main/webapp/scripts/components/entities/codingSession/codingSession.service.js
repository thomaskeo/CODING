'use strict';

angular.module('codingApp')
    .factory('CodingSession', function ($resource) {
        return $resource('api/codingSessions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.sessionStart = new Date(data.sessionStart);
                    data.sessionEnd = new Date(data.sessionEnd);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
