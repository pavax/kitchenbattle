'use strict';
angular.module('adminModule')
    .controller('EventResultCtrl', function ($scope, teams, courseBattleResults, juryVotes, $filter, Fullscreen) {

        var eventResultCtrl = this;

        eventResultCtrl.teams = teams;

        initModel();

        function initModel() {
            var teamIds = [];
            angular.forEach(teams, function (value) {
                teamIds.push(value.teamId);
            });
            eventResultCtrl.guestCourseResults = [];
            angular.forEach($filter("orderBy")(courseBattleResults, 'courseType', true), function (courseBattleResult) {
                eventResultCtrl.guestCourseResults.push(initGuestCourseResult(courseBattleResult.courseType, teamIds));
            });
            $scope.guestCourseResults = eventResultCtrl.guestCourseResults;

            eventResultCtrl.juryVoteResults = [];
            angular.forEach(juryVotes, function (juryVote) {
                eventResultCtrl.juryVoteResults.push(initJuryVote(juryVote, teamIds));
            });
            $scope.juryVoteResults = eventResultCtrl.juryVoteResults;


        }

        $scope.$watch('guestCourseResults', function () {
            eventResultCtrl.guestCourseTotalResult = initGuestCourseTotal(eventResultCtrl.guestCourseResults);
            eventResultCtrl.winnerResult = initWinnerResult();
        }, true);

        $scope.$watch('juryVoteResults', function () {
            eventResultCtrl.juryVotesTotalResult = initJuryVotesTotalResult(eventResultCtrl.juryVoteResults);
            eventResultCtrl.winnerResult = initWinnerResult();
        }, true);

        function initGuestCourseResult(courseType, teamIds) {
            var aCourseBattle = $filter("filter")(courseBattleResults, {courseType: courseType})[0];
            var guestCourseResult = {
                showResult: false,
                courseType: courseType,
                teamLeft: findCourseResult(teamIds[0], aCourseBattle),
                teamRight: findCourseResult(teamIds[1], aCourseBattle)
            };
            if (guestCourseResult.teamLeft && guestCourseResult.teamRight) {
                if (guestCourseResult.teamLeft.totalGuestVotes > guestCourseResult.teamRight.totalGuestVotes) {
                    guestCourseResult.teamLeft.points = 10;
                    guestCourseResult.teamRight.points = 0;
                    guestCourseResult.teamLeft.winner = true;
                } else if (guestCourseResult.teamRight.totalGuestVotes > guestCourseResult.teamLeft.totalGuestVotes) {
                    guestCourseResult.teamRight.points = 10;
                    guestCourseResult.teamLeft.points = 0;
                    guestCourseResult.teamRight.winner = true;
                } else {
                    // untentschieden
                    guestCourseResult.teamLeft.points = 5;
                    guestCourseResult.teamRight.points = 5;
                }
            }
            return  guestCourseResult
        }

        function initGuestCourseTotal(guestCourseResults) {
            var guestCourseTotalResult = {
                teamLeft: {totalPoints: 0, winner: false},
                teamRight: {totalPoints: 0, winner: false}
            };
            angular.forEach(guestCourseResults, function (guestCourseResult) {
                if (guestCourseResult.teamLeft && guestCourseResult.teamRight && guestCourseResult.showResult) {
                    guestCourseTotalResult.teamLeft.totalPoints += guestCourseResult.teamLeft.points;
                    guestCourseTotalResult.teamRight.totalPoints += guestCourseResult.teamRight.points;
                }
            });
            if (guestCourseTotalResult.teamLeft.totalPoints > guestCourseTotalResult.teamRight.totalPoints) {
                guestCourseTotalResult.teamLeft.winner = true;
            } else if (guestCourseTotalResult.teamRight.totalPoints > guestCourseTotalResult.teamLeft.totalPoints) {
                guestCourseTotalResult.teamRight.winner = true;
            }
            return guestCourseTotalResult;
        }

        function initJuryVotesTotalResult(juryVoteResults) {
            var juryVotesTotalResult = {
                teamLeft: {totalPoints: 0, winner: false},
                teamRight: {totalPoints: 0, winner: false}
            };
            angular.forEach(juryVoteResults, function (juryResult) {
                if (juryResult.teamLeft && juryResult.teamRight && juryResult.showResult) {
                    juryVotesTotalResult.teamLeft.totalPoints += juryResult.teamLeft.points;
                    juryVotesTotalResult.teamRight.totalPoints += juryResult.teamRight.points;
                }
            });
            if (juryVotesTotalResult.teamLeft.totalPoints > juryVotesTotalResult.teamRight.totalPoints) {
                juryVotesTotalResult.teamLeft.winner = true;
            } else if (juryVotesTotalResult.teamRight.totalPoints > juryVotesTotalResult.teamLeft.totalPoints) {
                juryVotesTotalResult.teamRight.winner = true;
            }
            return juryVotesTotalResult;
        }


        function findCourseResult(teamName, courseBattle) {
            if (courseBattle === undefined) {
                return undefined;
            }
            if (courseBattle.courseOne.teamId === teamName) {
                return courseBattle.courseOne;
            } else if (courseBattle.courseTwo.teamId === teamName) {
                return courseBattle.courseTwo;
            }
        }


        function initJuryVote(juryVote, teamIds) {
            return {
                showResult: false,
                juryName: juryVote.juryName,
                teamLeft: {
                    points: juryVote.teamId === teamIds[0] ? 5 : 0
                },
                teamRight: {
                    points: juryVote.teamId === teamIds[1] ? 5 : 0
                }
            };
        }

        function initWinnerResult() {
            var winnerResult = {
                teamLeft: {
                    totalPoints: eventResultCtrl.guestCourseTotalResult.teamLeft.totalPoints + eventResultCtrl.juryVotesTotalResult.teamLeft.totalPoints
                },
                teamRight: {
                    totalPoints: eventResultCtrl.guestCourseTotalResult.teamRight.totalPoints + eventResultCtrl.juryVotesTotalResult.teamRight.totalPoints
                }
            };
            winnerResult.teamLeft.winner = winnerResult.teamLeft.totalPoints > winnerResult.teamRight.totalPoints;
            winnerResult.teamRight.winner = winnerResult.teamLeft.totalPoints < winnerResult.teamRight.totalPoints;
            return winnerResult;
        }

    })
;