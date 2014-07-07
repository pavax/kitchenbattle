'use strict';

angular.module('commons.resources.teams', [])
    .factory('teamService', function ($http) {
        return {
            findAllTeams: function () {
                return $http.get('api/teams');
            },
            getTeam: function (teamId) {
                return $http.get('api/teams/' + teamId);
            },
            createTeam: function (name, description) {
                return $http.post('api/teams', {
                    name: name,
                    description: description
                });
            },
            deleteTeam: function (teamId) {
                return $http.delete('api/teams/' + teamId);
            }
        }
    });