'use strict';

angular.module('courseBattleVotingModule', []
).config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('courseBattleVoting', {
                url: "/course-battle-voting",
                template: "<div ui-view></div>",
                abstract: true
            })
            .state('courseBattleVoting.select', {
                url: "",
                templateUrl: "scripts/course-battle-voting/courseBattleVotingSelection.html",
                controller: 'CourseBattleVotingSelectionController as courseBattleSelection',
                resolve: {
                    battles: function (courseBattleSearchService) {
                        return courseBattleSearchService.findAllCourseBattlesWithDetails().then(function (response) {
                            return response.data;
                        });
                    }
                }
            })
            .state('courseBattleVoting.vote', {
                url: "/vote?battleId",
                templateUrl: "scripts/course-battle-voting/courseBattleVoting.html",
                controller: 'CourseBattleVotingCtrl as courseBattleVoting',
                resolve: {
                    battle: function (courseBattleService, $stateParams) {
                        return courseBattleService.getBattle($stateParams.battleId)
                            .then(function (successResponse) {
                                return successResponse.data;
                            });
                    },
                    courseOne: function (courseService, battle) {
                        return courseService.getCourse(battle.courseIdOne)
                            .then(function (successResponse) {
                                return successResponse.data;
                            });
                    },
                    courseTwo: function (courseService, battle) {
                        return courseService.getCourse(battle.courseIdTwo)
                            .then(function (successResponse) {
                                return successResponse.data;
                            });
                    }
                }
            })
    });
