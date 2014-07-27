'use strict';

angular.module('courseBattleVotingModule', []
).config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('courseBattleVoting', {
                url: "/course-battle-voting",
                template: "<div ui-view></div>",
                abstract: true
            })
            .state('courseBattleVoting.event', {
                url: "",
                templateUrl: "scripts/course-battle-voting/eventSelection.html",
                controller: 'EventSelectionController as eventSelectionCtrl',
                resolve: {
                    events: function (eventsService) {
                        return eventsService.findAllEvents()
                            .then(function (response) {
                                return response.data;
                            });
                    }
                }
            })
            .state('courseBattleVoting.battles', {
                url: "/battles",
                template: "<div ui-view></div>",
                abstract: true
            })
            .state('courseBattleVoting.battles.master', {
                url: "?eventId",
                templateUrl: "scripts/course-battle-voting/courseBattleSelection.html",
                controller: 'CourseBattleSelectionController as courseBattleSelectionCtrl',
                resolve: {
                    selectedEventId: function ($stateParams) {
                        if ($stateParams.eventId) {
                            return $stateParams.eventId;
                        } else {
                            return null;
                        }
                    },
                    battles: function (courseBattleSearchService, selectedEventId) {
                        if (selectedEventId === null) {
                            return null;
                        }
                        return courseBattleSearchService.findAllCourseBattlesWithDetails(selectedEventId).then(function (response) {
                            return response.data;
                        });
                    }
                }
            })
            .state('courseBattleVoting.battles.vote', {
                url: "/:battleId/vote",
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
