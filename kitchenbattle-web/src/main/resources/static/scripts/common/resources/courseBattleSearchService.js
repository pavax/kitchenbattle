'use strict';

angular.module('commons.resources.courseBattleResult', [])
    .factory('courseBattleSearchService', function ($http) {
        return {
            findCourseBattleResults: function (battleId) {
                return $http.get('api/course-battles-search/results/' + battleId);
            },
            getAllCourseBattleResult: function (battleId) {
                return $http.get('api/course-battles-search/results');
            },
            findAllCourseBattlesWithDetails: function (eventId) {
                return $http.get('api/course-battles-search', {params: {eventId: eventId}});
            }
        }
    });