'use strict';

angular.module('commons.resources.currentUser', [])
    .factory('currentUserService', function ($http) {
        return {
            getCurrentUser: function () {
                return $http.get('api/current-user')
                    .then(function (successResponse) {
                        var currentUser = successResponse.data;
                        currentUser.isAdmin = currentUser.roles.indexOf('ROLE_ADMIN') > -1;
                        currentUser.isUser = currentUser.roles.indexOf('ROLE_USER') > -1;
                        return successResponse;
                    });
            }
        }
    })
;