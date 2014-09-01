'use strict';

angular.module('kitchenbattleWebApp')
    .controller('menuCtrl', function (currentUserService, $rootScope) {
        var menuCtrl = this;

        this.menu = {
            admin: {
                selectedEventId: undefined
            }
        };
        currentUserService.getCurrentUser()
            .then(function (successResponse) {
                menuCtrl.currentUser = successResponse.data;
            });

        $rootScope.$on('admin.selectedEvent', function (event, data) {
            menuCtrl.menu.admin.selectedEventId = data;
        });
    });