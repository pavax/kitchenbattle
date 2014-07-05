'use strict';

angular.module('battleResultModule', []
).config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('battleResult', {
                url: "/battle-result",
                template: "<div ui-view></div>",
                abstract: true
            })
            .state('battleResult.show', {
                url: "",
                templateUrl: "scripts/kitchenbattle-result/battleResult.html",
                controller: 'battleResultCtrl as battleResult',
                resolve: {
                    courseBattleResults: function (courseBattleResultService) {
                        return courseBattleResultService.getAllResults().then(function (response) {
                            return response.data;
                        });
                    },
                    juryVotes: function (juryTeamVoteService) {
                        return juryTeamVoteService.getAllJuryVotes().then(function (response) {
                            return response.data;
                        });
                    }
                }
            })
    });
