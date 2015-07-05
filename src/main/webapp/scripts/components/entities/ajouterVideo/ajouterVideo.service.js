'use strict';

angular.module('codingApp')
    .factory('AjouterVideo', function ($resource) {
        return $resource('api/ajouterVideos/:id', {}, {
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
