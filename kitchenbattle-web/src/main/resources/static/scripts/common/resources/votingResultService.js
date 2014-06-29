'use strict';

angular.module('commons.resources.votingResult', []);

angular.module('commons.resources.votingResult')
    .factory('votingResultService', function ($http) {
        return {
            getVotingResultForBattle: function (battleId) {
                return $http.get('api/voting-results/',
                    {
                        params: {
                            'battleId': battleId
                        }
                    }
                );
            }
        }
    });