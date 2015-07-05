'use strict';

angular.module('codingApp')
    .factory('SeancePrive', function ($resource) {
        return $resource('api/seancePrives/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.datedebut = new Date(data.datedebut);
                    data.datefin = new Date(data.datefin);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
