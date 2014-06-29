'use strict';

var app = angular.module('votingResultsModule', ['commons.resources.battle', 'commons.resources.votingResult','commons.resources.course']);

app.config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider
        .state('votingresults', {
            url: "/votingresults",
            template: "<div ui-view></div>",
            abstract: true
        })
        .state('votingresults.select', {
            url: "",
            templateUrl: "views/voting-results/battleSelection.html",
            controller: 'BattleSelectionController as battleSelection'
        })
        .state('votingresults.show', {
            url: "/show?battleId",
            templateUrl: "views/voting-results/showResults.html",
            controller: 'ShowBattleVotingResultsController as showBattleVotingResult',
            resolve: {
                battleId: function ($stateParams) {
                    return $stateParams.battleId
                }
            }
        })
});
