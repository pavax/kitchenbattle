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
            deleteVote: function (juryVoteId) {
                return $http.delete('api/jury-team-votes/' + juryVoteId);
            },
            findAllJuryVotesForEvent: function (eventId) {
                return $http.get('api/jury-team-votes', { params: {eventId: eventId}});
            }
        }
    });