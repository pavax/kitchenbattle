'use strict';

angular.module('commons.resources.courseBattleResult', [])
    .factory('courseBattleResultService', function ($http) {
        return {
            getResultForBattle: function (battleId) {
                return $http.get('api/course-battle-result/' + battleId);
            },
            getAllResults: function (battleId) {
                return $http.get('api/course-battle-result');
            }
        }
    });