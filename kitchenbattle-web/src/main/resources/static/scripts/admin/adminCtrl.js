'use strict';
angular.module('adminModule')
    .controller('AdminController', function ($scope, $state, events, selectedEventId) {

        this.events = events;

        this.selectedEventId = selectedEventId;

        this.selectEvent = function (selectedEventId) {
            $state.go($state.current.name, {eventId: selectedEventId})
        }
    });