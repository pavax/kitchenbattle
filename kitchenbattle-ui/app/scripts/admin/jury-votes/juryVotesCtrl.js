'use strict';
angular.module('adminModule')
    .controller('JuryVotesCtrl', function ($scope, teams, juryVotes, juryTeamVoteService, $state, $filter) {
        var juryVotesCtrl = this;

        juryVotesCtrl.teams = teams;

        juryVotesCtrl.juryVotes = juryVotes;

        juryVotesCtrl.newVote = {
            juryName: undefined,
            teamId: undefined
        };

        angular.forEach(juryVotes, function (juryVote) {
            var foundTeams = $filter("filter")(teams, {teamId: juryVote.teamId});
            if (foundTeams.length > 0) {
                juryVote.teamName = foundTeams[0].name;
            }
        });

        juryVotesCtrl.toggleNewJuryVoteForm = function () {
            $scope.$broadcast('show-errors-reset');
            juryVotesCtrl.showNewJuryVoteForm = !juryVotesCtrl.showNewJuryVoteForm;
        };

        juryVotesCtrl.saveVote = function () {
            $scope.$broadcast('show-errors-check-validity');
            if ($scope.juryVoteForm.$valid) {
                juryTeamVoteService.voteTeam(juryVotesCtrl.newVote.teamId, juryVotesCtrl.newVote.juryName, 5)
                    .then(function (successResponse) {
                        $state.forceReload();
                    });
            }
        };

        juryVotesCtrl.deleteVote = function (juryVoteId) {
            if (window.confirm("Sure?")) {
                juryTeamVoteService.deleteVote(juryVoteId)
                    .then(function (successResponse) {
                        $state.forceReload();
                    });
            }
        }


    })
;