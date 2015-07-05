'use strict';

angular.module('codingApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
