'use strict';

angular.module('kitchenbattleWebApp')
    .controller('menuCtrl', function (currentUserService) {
        var menuCtrl = this;
        currentUserService.getCurrentUser()
            .then(function (successResponse) {
                menuCtrl.currentUser = successResponse.data;
            });
    });