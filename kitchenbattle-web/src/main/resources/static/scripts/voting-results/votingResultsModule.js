'use strict';

angular.module('votingResultsModule',
    [
        'commons.resources.battle',
        'commons.resources.votingResult',
        'commons.resources.course',
        'commons.directives.courseBattles'
    ]
).config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('votingresults', {
                url: "/votingresults",
                template: "<div ui-view></div>",
                abstract: true
            })
            .state('votingresults.select', {
                url: "",
                templateUrl: "scripts/voting-results/courseBattleSelection.html",
                controller: 'CourseBattleSelectionController as courseBattleSelection',
                resolve: {
                    battles: function (battleService) {
                        return battleService.getAllBattles().then(function (response) {
                            return response.data;
                        });
                    }
                }
            })
            .state('votingresults.show', {
                url: "/show?battleId",
                templateUrl: "scripts/voting-results/showCourseBattleVotringResults.html",
                controller: 'ShowCourseBattleVotingResultsController as courseBattleVotingResult',
                resolve: {
                    battleId: function ($stateParams) {
                        return $stateParams.battleId
                    }
                }
            })
    });
