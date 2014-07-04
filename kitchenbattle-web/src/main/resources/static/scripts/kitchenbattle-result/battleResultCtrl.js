'use strict';

angular.module('battleResultModule')
    .controller('battleResultCtrl', function (courseBattleResults) {
        var courseBattleResultCtrl = this;

        this.teams = [];
        this.courseBattleResults = courseBattleResults;

        function init() {
            var processedTeamIds = [];

            for (var i = 0; i < courseBattleResults.length; i++) {
                var courseBattle = courseBattleResults[i];
                var courseOne = courseBattle.courseOne;
                var courseTwo = courseBattle.courseTwo;

                processCourseBattleTeam(courseOne);
                processCourseBattleTeam(courseTwo);
                processCourseBattleWinner();
            }
            processTeamWinner();

            function processCourseBattleWinner() {
                if (courseOne.totalGuestVotes > courseTwo.totalGuestVotes) {
                    courseBattle.winnerTeamId = courseOne.teamId;
                } else if (courseTwo.totalGuestVotes > courseOne.totalGuestVotes) {
                    courseBattle.winnerTeamId = courseTwo.teamId;
                }
            }

            function processTeamWinner() {
                var winnerIndex = 0;
                var currentMax = 0;
                for (var i = 0; i < courseBattleResultCtrl.teams.length; i++) {
                    var team = courseBattleResultCtrl.teams[i];
                    if (team.total > currentMax) {
                        winnerIndex = i;
                        currentMax = team.total;
                    }
                }
                courseBattleResultCtrl.teams[winnerIndex].winner = true;
            }

            function processCourseBattleTeam(course) {
                var indexOf = processedTeamIds.indexOf(course.teamId);
                if (indexOf === -1) {
                    processedTeamIds.push(course.teamId);
                    courseBattleResultCtrl.teams.push({teamId: course.teamId, teamName: course.teamName, total: course.totalGuestVotes});
                } else {
                    courseBattleResultCtrl.teams[indexOf].total += course.totalGuestVotes;
                }
            }
        }

        init();

        this.findCourseResultForTeam = function (teamId, courseBattle) {
            var courseOne = courseBattle.courseOne;
            var courseTwo = courseBattle.courseTwo;
            if (courseOne.teamId === teamId) {
                return courseOne;
            } else if (courseTwo.teamId === teamId) {
                return courseTwo;
            }
        };

    });