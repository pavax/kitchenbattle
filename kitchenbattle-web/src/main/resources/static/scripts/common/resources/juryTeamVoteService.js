'use strict';

angular.module('commons.resources.juryTeamVote', [])
    .factory('juryTeamVoteService', function ($http) {
        return {
            voteTeam: function (teamId, juryName, votes) {
                return $http.post('api/jury-team-votes', {
                        teamId: teamId,
                        juryName: juryName,
                        votes: votes
                    }
                );
            },
            findAllJuryVotesForEvent: function (eventId) {
                return $http.get('api/jury-team-votes', { params: {eventId: eventId}});
            }
        }
    });