'use strict';

angular.module('commons.resources.courseBattle', [])
    .factory('courseBattleService', function ($http) {
        return {
            getAllCourseBattles: function () {
                return $http.get('api/course-battles/search/allBattles');
            },
            getBattle: function (battleId) {
                return $http.get('api/course-battles/' + battleId);
            }

        }
    });