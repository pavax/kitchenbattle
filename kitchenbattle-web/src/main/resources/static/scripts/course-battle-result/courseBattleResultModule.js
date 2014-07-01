'use strict';

angular.module('courseBattleResultModule', []
).config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('courseBattleResults', {
                url: "/course-battle-results",
                template: "<div ui-view></div>",
                abstract: true
            })
            .state('courseBattleResults.select', {
                url: "",
                templateUrl: "scripts/course-battle-result/courseBattleSelection.html",
                controller: 'CourseBattleSelectionController as courseBattleSelection',
                resolve: {
                    battles: function (courseBattleService) {
                        return courseBattleService.getAllBattles().then(function (response) {
                            return response.data;
                        });
                    }
                }
            })
            .state('courseBattleResults.show', {
                url: "/show?battleId",
                templateUrl: "scripts/course-battle-result/showCourseBattleVotringResults.html",
                controller: 'ShowCourseBattleVotingResultsController as courseBattleVotingResult',
                resolve: {
                    battleId: function ($stateParams) {
                        return $stateParams.battleId
                    }
                }
            })
    });
