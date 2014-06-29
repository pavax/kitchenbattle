'use strict';

angular.module('commons.resources.battle', []);

angular.module('commons.resources.battle')
    .factory('battleService', function ($http) {
        return {
            getAllBattles: function() {
                return $http.get('api/battles/search/allBattles');
            }
        }
    });