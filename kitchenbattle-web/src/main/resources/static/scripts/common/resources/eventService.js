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
            createEvent: function (name, eventDate) {
                return $http.post('api/events', {name: name, eventDate: eventDate});
            },
            deleteEvent: function (eventId) {
                return $http.delete('api/events/' + eventId);
            },
            updateEvent: function (eventData) {
                return $http.put('api/events/' + eventData.eventId, eventData);
            }
        }
    });