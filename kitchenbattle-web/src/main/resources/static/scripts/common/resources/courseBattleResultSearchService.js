'use strict';

angular.module('commons.resources.courseBattleResultSearch', [])
    .factory('courseBattleResultSearchService', function ($http) {
        return {
            getResultForBattle: function (battleId) {
                return $http.get('api/course-battle-result-search',
                    {
                        params: {
                            'battleId': battleId
                        }
                    }
                );
            }
        }
    });