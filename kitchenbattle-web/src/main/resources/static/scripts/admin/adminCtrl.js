'use strict';
angular.module('adminModule')
    .controller('AdminController', function ($rootScope, $scope, $state, events, selectedEventId, selectedEvent) {

        this.events = events;

        this.selectedEventId = selectedEventId;

        this.selectedEvent = selectedEvent;

        $rootScope.$broadcast('admin.selectedEvent', selectedEventId);

        this.selectEvent = function (selectedEventId) {
            $state.go($state.current.name, {eventId: selectedEventId})
        };
    });