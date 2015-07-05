'use strict';

angular.module('codingApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


