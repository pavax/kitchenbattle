'use strict';

angular.module('battleResultModule')
    .controller('battleResultCtrl', function (courseBattleResults, juryVotes) {
        var courseBattleResultCtrl = this;

        this.teams = [];
        this.courseBattleResults = courseBattleResults;
        this.juryVotes = juryVotes;


        function init() {
            for (var i = 0; i < courseBattleResults.length; i++) {
                var courseBattle = courseBattleResults[i];
                var courseOne = courseBattle.courseOne;
                var courseTwo = courseBattle.courseTwo;
                if (courseOne.totalGuestVotes > courseTwo.totalGuestVotes) {
                    courseBattle.winnerTeamId = courseOne.teamId;
                } else if (courseTwo.totalGuestVotes > courseOne.totalGuestVotes) {
                    courseBattle.winnerTeamId = courseTwo.teamId;
                }
                processCourseBattleTeam(courseOne);
                processCourseBattleTeam(courseTwo);

            }
            processTotalJuryVotes();
            processTotalVotes();
            courseBattleResultCtrl.teams[determineTeamWinnerForProperty('totalCourseBattleVotes')].courseBattleVotesWinner = true;
            courseBattleResultCtrl.teams[determineTeamWinnerForProperty('totalJuryVotes')].juryVotesWinner = true;
            courseBattleResultCtrl.teams[determineTeamWinnerForProperty('total')].totalVotesWinner = true;

            function processTotalJuryVotes() {
                for (var i = 0; i < juryVotes.length; i++) {
                    var juryVote = juryVotes[i];
                    var foundTeam = find(courseBattleResultCtrl.teams, function (aTeam) {
                        return aTeam.teamId === juryVote.teamId;
                    });
                    if (foundTeam.totalJuryVotes === undefined) {
                        foundTeam.totalJuryVotes = juryVote.votes;
                    } else {
                        foundTeam.totalJuryVotes += juryVote.votes;
                    }
                }
            }

            function processTotalVotes() {
                for (var i = 0; i < courseBattleResultCtrl.teams.length; i++) {
                    var team = courseBattleResultCtrl.teams[i];
                    team.total = team.totalCourseBattleVotes + team.totalJuryVotes;
                }
            }

            function determineTeamWinnerForProperty(property) {
                var winnerIndex = 0;
                var currentMax = 0;
                for (var i = 0; i < courseBattleResultCtrl.teams.length; i++) {
                    var team = courseBattleResultCtrl.teams[i];
                    var numberProperty = team[property];
                    if (numberProperty > currentMax) {
                        winnerIndex = i;
                        currentMax = numberProperty;
                    }
                }
                return winnerIndex;
            }

            function processCourseBattleTeam(course) {
                var foundTeam = find(courseBattleResultCtrl.teams, function (aTeam) {
                    return aTeam.teamId === course.teamId;
                });
                if (angular.isUndefined(foundTeam)) {
                    courseBattleResultCtrl.teams.push({teamId: course.teamId, teamName: course.teamName, totalCourseBattleVotes: course.totalGuestVotes});
                } else {
                    foundTeam.totalCourseBattleVotes += course.totalGuestVotes;
                }
            }

        }

        init();


        function find(collection, predicate) {
            var length = collection.length;
            for (var j = 0; j < length; j++) {
                if (predicate(collection[j]) == true) {
                    return collection[j];
                }
            }
            return undefined;
        }


        function filter(collection, predicate) {
            var result = [];
            var length = collection.length;
            for (var j = 0; j < length; j++) {
                if (predicate(collection[j]) == true) {
                    result.push(collection[j]);
                }
            }
            return result;
        }


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