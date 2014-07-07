'use strict';

angular.module('adminModule', []
).config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('admin', {
                url: "/admin",
                templateUrl: "scripts/admin/adminMenu.html",
                abstract: false
            })
            .state('admin.teams', {
                url: "/teams",
                templateUrl: "scripts/admin/teams/manageTeams.html",
                controller: 'TeamController as teamCtrl',
                resolve: {
                    teams: function (teamService) {
                        return teamService.findAllTeams().then(function (response) {
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
                    teams: function (teamService) {
                        return teamService.findAllTeams().then(function (response) {
                            return response.data;
                        });
                    },
                    selectedTeam: function (teamService, $stateParams) {
                        if ($stateParams.teamId) {
                            return teamService.getTeam($stateParams.teamId).then(function (response) {
                                return response.data;
                            });
                        } else {
                            return null;
                        }
                    },
                    courses: function (courseService, selectedTeam) {
                        if (selectedTeam) {
                            return courseService.findCoursesByTeam(selectedTeam.teamId)
                                .then(function (response) {
                                    return response.data;
                                });
                        } else {
                            return null;
                        }

                    }
                }
            })
            .state('admin.coursebattles', {
                url: "/course-battles",
                templateUrl: "scripts/admin/course-battles/manageCourseBattles.html",
                controller: 'CourseBattlesController as courseBattlesCtrl',
                resolve: {
                    courseBattles: function (courseBattleService) {
                        return courseBattleService.getAllCourseBattles().then(function (response) {
                            return response.data;
                        });
                    },
                    teams: function (teamService) {
                        return teamService.findAllTeams().then(function (response) {
                            return response.data;
                        });
                    }
                }
            })
    });
