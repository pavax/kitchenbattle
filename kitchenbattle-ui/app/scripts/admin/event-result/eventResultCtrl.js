'use strict';
angular.module('adminModule')
    .controller('EventResultCtrl', function ($scope, selectedEvent, teams, courseBattleResults, juryVotes, $filter, juryTeamVoteService, $timeout, Fullscreen) {

        var eventResultCtrl = this;

        eventResultCtrl.selectedEvent = selectedEvent;

        eventResultCtrl.teams = teams;

        eventResultCtrl.fullscreen = Fullscreen;

        var selectedEventId = selectedEvent.eventId;

        initModel();

        function initModel() {
            var teamIds = teams.map(function (aTeam) {
                return aTeam.teamId;
            });
            eventResultCtrl.teamIds = teamIds;

            eventResultCtrl.guestCourseResults = [];
            angular.forEach($filter("orderBy")(courseBattleResults, 'courseType', true), function (courseBattleResult) {
                eventResultCtrl.guestCourseResults.push(initGuestCourseResult(courseBattleResult, teamIds));
            });
            $scope.guestCourseResults = eventResultCtrl.guestCourseResults;

            eventResultCtrl.juryVoteResults = [];
            angular.forEach(juryVotes, function (juryVote) {
                eventResultCtrl.juryVoteResults.push(initJuryVote(juryVote, teamIds));
            });
            $scope.juryVoteResults = eventResultCtrl.juryVoteResults;
        }

        $scope.$watch('guestCourseResults', function (updatedGuestCourseResults) {
            eventResultCtrl.guestCourseTotalResult = initTotalResultEntry(updatedGuestCourseResults);
            eventResultCtrl.winnerResult = initTotalResultEntry([eventResultCtrl.guestCourseTotalResult, eventResultCtrl.juryVotesTotalResult]);
        }, true);

        $scope.$watch('juryVoteResults', function (updatedJuryVoteResults) {
            eventResultCtrl.juryVotesTotalResult = initTotalResultEntry(updatedJuryVoteResults);
            eventResultCtrl.winnerResult = initTotalResultEntry([eventResultCtrl.guestCourseTotalResult, eventResultCtrl.juryVotesTotalResult]);
        }, true);

        function initGuestCourseResult(aCourseBattle, teamIds) {
            var guestVoteEntry = {
                id: aCourseBattle.battleId,
                showResult: false,
                courseType: aCourseBattle.courseType,
                teamLeft: teamIds[0] === aCourseBattle.courseOne.teamId ? aCourseBattle.courseOne : aCourseBattle.courseTwo,
                teamRight: teamIds[1] === aCourseBattle.courseOne.teamId ? aCourseBattle.courseOne : aCourseBattle.courseTwo,
            };
            if (guestVoteEntry.teamLeft.totalGuestVotes > guestVoteEntry.teamRight.totalGuestVotes) {
                guestVoteEntry.teamLeft.points = 10;
                guestVoteEntry.teamRight.points = 0;
                guestVoteEntry.teamLeft.winner = true;
            } else if (guestVoteEntry.teamRight.totalGuestVotes > guestVoteEntry.teamLeft.totalGuestVotes) {
                guestVoteEntry.teamRight.points = 10;
                guestVoteEntry.teamLeft.points = 0;
                guestVoteEntry.teamRight.winner = true;
            } else {
                // unentschieden
                guestVoteEntry.teamLeft.points = 5;
                guestVoteEntry.teamRight.points = 5;
            }
            return  guestVoteEntry
        }

        function initJuryVote(juryVote, teamIds, showResult) {
            showResult = showResult || false;
            return {
                id: juryVote.id,
                showResult: showResult,
                juryName: juryVote.juryName,
                teamLeft: {
                    points: juryVote.teamId === teamIds[0] ? 5 : 0,
                    winner: juryVote.teamId === teamIds[0]
                },
                teamRight: {
                    points: juryVote.teamId === teamIds[1] ? 5 : 0,
                    winner: juryVote.teamId === teamIds[1]
                }
            };
        }

        function initTotalResultEntry(resultEntries) {
            resultEntries = resultEntries.filter(function (n) {
                return n != undefined
            });
            var totalResultEntry = {
                teamLeft: {points: 0, winner: false},
                teamRight: {points: 0, winner: false}
            };
            angular.forEach(resultEntries, function (result) {
                if (result.teamLeft && result.teamRight && (result.showResult !== undefined ? result.showResult : true)) {
                    totalResultEntry.teamLeft.points += result.teamLeft.points;
                    totalResultEntry.teamRight.points += result.teamRight.points;
                }
            });
            if (totalResultEntry.teamLeft.points > totalResultEntry.teamRight.points) {
                totalResultEntry.teamLeft.winner = true;
            } else if (totalResultEntry.teamRight.points > totalResultEntry.teamLeft.points) {
                totalResultEntry.teamRight.winner = true;
            }
            return totalResultEntry;
        }

        function refreshJuryVotes() {
            juryTeamVoteService.findAllJuryVotesForEvent(selectedEventId)
                .then(function (successResponse) {
                    var newJuryVotes = successResponse.data;
                    var existingJuryVoteResults = eventResultCtrl.juryVoteResults;
                    angular.forEach(newJuryVotes, function (newJuryVote) {
                        if (!containsJuryVoteId(newJuryVote.id, existingJuryVoteResults)) {
                            // a new jury vote occured
                            existingJuryVoteResults.push(initJuryVote(newJuryVote, eventResultCtrl.teamIds, true))
                        }
                    });
                    angular.forEach(existingJuryVoteResults, function (existingJuryVote, key) {
                        if (!containsJuryVoteId(existingJuryVote.id, newJuryVotes)) {
                            // a jury vote has been deleted
                            existingJuryVoteResults.splice(key, 1);
                        }
                    });
                });
            function containsJuryVoteId(juryVoteId, juryVoteResults) {
                return juryVoteResults.some(function (aJuryVoteResult) {
                    return aJuryVoteResult.id === juryVoteId;
                })
            }
        }

        function startInterval() {
            var timer = $timeout(function () {
                refreshJuryVotes();
                startInterval();
            }, 2000);
            $scope.$on('$destroy', function () {
                $timeout.cancel(timer);
            });
        }

        startInterval();
    });