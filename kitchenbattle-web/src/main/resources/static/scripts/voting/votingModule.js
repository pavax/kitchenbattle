'use strict';

angular.module('votingModule',
    [
        'commons.resources.battle',
        'commons.resources.course',
        'commons.resources.courseVoting'
    ]
).config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('voting', {
                url: "/voting",
                template: "<div ui-view></div>",
                abstract: true
            })
            .state('voting.select', {
                url: "",
                templateUrl: "scripts/voting/courseBattleVotingSelection.html",
                controller: 'CourseBattleVotingSelectionController as courseBattleSelection',
                resolve: {
                    battles: function (battleService) {
                        return battleService.getAllBattles().then(function (response) {
                            return response.data;
                        });
                    }
                }
            })
            .state('voting.vote', {
                url: "/vote?battleId",
                templateUrl: "scripts/voting/courseBattleVoting.html",
                controller: 'CourseBattleVotingCtrl as courseBattleVoting',
                resolve: {
                    battle: function (battleService, $stateParams) {
                        return battleService.getBattle($stateParams.battleId)
                            .then(function (successResponse) {
                                return successResponse.data;
                            });
                    },
                    courseOne: function (courseService, battle) {
                        return courseService.getCourse(battle.courseOneId.value)
                            .then(function (successResponse) {
                                return successResponse.data;
                            });
                    },
                    courseTwo: function (courseService, battle) {
                        return courseService.getCourse(battle.courseTwoId.value)
                            .then(function (successResponse) {
                                return successResponse.data;
                            });
                    }
                }
            })
    });
