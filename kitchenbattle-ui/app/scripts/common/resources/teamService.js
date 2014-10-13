'use strict';

angular.module('commons.resources.teams', [])
    .factory('teamService', function ($http) {
        return {
            findAllTeams: function (eventId) {
                return $http.get('api/teams', {params: {eventId: eventId}});
            },
            getTeam: function (teamId) {
                return $http.get('api/teams/' + teamId);
            },
            createTeam: function (name, description,eventId) {
                return $http.post('api/teams', {
                    name: name,
                    description: description,
                    eventId: eventId
                });
            },
            deleteTeam: function (teamId) {
                return $http.delete('api/teams/' + teamId);
            }
        }
    });