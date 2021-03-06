'use strict';
angular.module('adminModule')
    .controller('CourseBattlesController', function ($scope, courseBattles, teams, courseService, courseBattleService, $state, courseBattleVoteService) {
        this.courseBattles = courseBattles;
        var courseBattlesCtrl = this;
        this.teams = teams;
        this.showCreateNewTeamForm = false;
        this.toogleNewCourseBattleForm = function () {
            courseBattlesCtrl.showCreateNewCourseBattleForm = !courseBattlesCtrl.showCreateNewCourseBattleForm;
            courseBattlesCtrl.newCourseBattle = {
                courseType: undefined,
                courseOne: {
                    selectedCourseId: undefined,
                    selectedTeam: undefined,
                    possibleTeams: angular.copy(teams),
                    possibleCourses: undefined
                },
                courseTwo: {
                    selectedCourseId: undefined,
                    selectedTeam: undefined,
                    possibleTeams: angular.copy(teams),
                    possibleCourses: undefined
                }
            };
        };

        this.select = function (battle) {
            console.log(battle);
        };

        this.selectedBattleCourseType = function () {
            courseBattlesCtrl.newCourseBattle.courseOne.selectedCourseId = undefined;
            courseBattlesCtrl.newCourseBattle.courseTwo.selectedCourseId = undefined;
        };

        this.selectTeamForCourseOne = function (selectedTeam) {
            courseService.findCoursesByTeam(selectedTeam.teamId).then(function (successResponse) {
                courseBattlesCtrl.newCourseBattle.courseOne.selectedCourseId = undefined;
                courseBattlesCtrl.newCourseBattle.courseOne.possibleCourses = successResponse.data;
            });
            courseBattlesCtrl.newCourseBattle.courseTwo.possibleTeams = angular.copy(teams);
            courseBattlesCtrl.newCourseBattle.courseTwo.possibleCourses = undefined;
            removeTeamFromArray(selectedTeam, courseBattlesCtrl.newCourseBattle.courseTwo.possibleTeams);
        };

        this.selectTeamForCourseTwo = function (selectedTeam) {
            courseService.findCoursesByTeam(selectedTeam.teamId).then(function (successResponse) {
                courseBattlesCtrl.newCourseBattle.courseTwo.selectedCourseId = undefined;
                courseBattlesCtrl.newCourseBattle.courseTwo.possibleCourses = successResponse.data;
            });
        };

        this.updateCourseBattleState = function (courseBattleId, newStatus) {
            courseBattleService.updateCourseBattleState(courseBattleId, newStatus)
                .then(function () {
                    $state.forceReload();
                });
        };

        this.deleteCourseBattle = function (courseBattleId) {
            if (window.confirm("Sure?")) {
                courseBattleService.deleteCourseBattle(courseBattleId)
                    .then(function () {
                        $state.forceReload();
                    });
            }
        };

        this.clearVotes = function (courseBattleId) {
            if (window.confirm("Sure?")) {
                courseBattleVoteService.clearVotes(courseBattleId);
            }
        };

        this.showResults = function (courseBattleId) {
            $state.go('admin.coursebattles.result', {battleId: courseBattleId})
        };

        this.showCard = function (courseBattleId) {
            $state.go('courseBattleVoting.battles.vote', {battleId: courseBattleId})
        };

        this.saveNewCourseBattle = function () {
            if ($scope.createNewCourseBattleForm.$valid) {
                courseBattleService.createNewCourseBattle(
                    courseBattlesCtrl.newCourseBattle.courseOne.selectedCourseId,
                    courseBattlesCtrl.newCourseBattle.courseTwo.selectedCourseId,
                    courseBattlesCtrl.newCourseBattle.courseType
                )
                    .success(function () {
                        $state.forceReload();
                    })
                    .error(function (error) {
                        alert("Oops an Error occured: " + error.message);
                    });
            }

        };

        function removeTeamFromArray(team, teamArray) {
            var index = -1;
            for (var i = 0, len = teamArray.length; i < len; i++) {
                if (teamArray[i].teamId === team.teamId) {
                    index = i;
                    break;
                }
            }
            teamArray.splice(index, 1);
        }


    })
;