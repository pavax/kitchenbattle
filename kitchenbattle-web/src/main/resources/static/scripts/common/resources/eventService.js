'use strict';

angular.module('commons.resources.events', [])
    .factory('eventsService', function ($http) {
        return {
            findAllEvents: function () {
                return $http.get('api/events');
            },
            getEvent: function (eventId) {
                return $http.get('api/events/' + eventId);
            },
            createEvent: function () {
                return $http.post('api/events', {name: name, eventDate: eventDate});
            }
        }
    });