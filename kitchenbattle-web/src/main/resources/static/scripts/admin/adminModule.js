'use strict';

angular.module('adminModule', []
).config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('admin', {
                url: "/admin?eventId",
                templateUrl: "scripts/admin/adminMenu.html",
                controller: 'AdminController as adminCtrl',
                abstract: true,
                resolve: {
                    events: function (eventsService) {
                        return eventsService.findAllEvents().then(function (response) {
                            return response.data;
                        });
                    },
                    selectedEventId: function ($stateParams) {
                        if ($stateParams.eventId) {
                            return $stateParams.eventId;
                        } else {
                            return null;
                        }
                    },
                    selectedEvent: function (selectedEventId, eventsService) {
                        if (selectedEventId) {
                            return eventsService.getEvent(selectedEventId).then(function (response) {
                                return response.data;
                            });
                        } else {
                            return null;
                        }
                    }
                }
            })
            .state('admin.teams', {
                url: "/teams",
                templateUrl: "scripts/admin/teams/manageTeams.html",
                controller: 'TeamController as teamCtrl',
                resolve: {
                    teams: function (teamService, selectedEventId) {
                        if (selectedEventId === null) {
                            return null;
                        }
                        return teamService.findAllTeams(selectedEventId).then(function (response) {
                            return response.data;
                        });
                    }
                }
            })
            .state('admin.courses', {
                url: "/courses?teamId",
                templateUrl: "scripts/admin/courses/manageCourses.html",
                controller: 'CoursesController as coursesCtrl',
                resolve: {
                    teams: function (teamService, selectedEventId) {
                        if (selectedEventId === null) {
                            return null;
                        }
                        return teamService.findAllTeams(selectedEventId).then(function (response) {
                            return response.data;
                        });
                    },
                    courses: function (courseService, selectedEventId) {
                        if (selectedEventId === null) {
                            return null;
                        }
                        return courseService.findCoursesByEvent(selectedEventId).then(function (response) {
                            return response.data;
                        });
                    }
                }
            })
            .state('admin.coursebattles', {
                url: "/course-battles",
                template: '<div ui-view></div>',
                abstract: true
            })
            .state('admin.coursebattles.master', {
                url: "",
                templateUrl: "scripts/admin/course-battles/manageCourseBattles.html",
                controller: 'CourseBattlesController as courseBattlesCtrl',
                resolve: {
                    courseBattles: function (courseBattleSearchService, selectedEventId) {
                        return courseBattleSearchService.findAllCourseBattlesWithDetails(selectedEventId).then(function (response) {
                            return response.data;
                        });
                    },
                    teams: function (teamService, selectedEventId) {
                        return teamService.findAllTeams(selectedEventId).then(function (response) {
                            return response.data;
                        });
                    }
                }
            })
            .state('admin.coursebattles.result', {
                url: "/:battleId/results",
                templateUrl: "scripts/admin/course-battles/courseBattleVotingResult.html",
                controller: 'CourseBattleResultController as courseBattleResultCtrl',
                resolve: {
                    battleId: function ($stateParams) {
                        return $stateParams.battleId
                    }
                }
            })
    });
