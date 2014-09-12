'use strict';
angular.module('adminModule')
    .controller('EventResultCtrl', function ($scope, teams, courseBattleResults, $filter) {

        var eventResultCtrl = this;

        initModel();

        this.teams = teams;

        function initModel() {
            var teamIds = [];
            angular.forEach(teams, function (value) {
                teamIds.push(value.teamId);
            });
            eventResultCtrl.guestCourseResults = [];
            eventResultCtrl.guestCourseResults.push(initGuestCourseResult('STARTER', teamIds));
            eventResultCtrl.guestCourseResults.push(initGuestCourseResult('MAIN', teamIds));
            eventResultCtrl.guestCourseResults.push(initGuestCourseResult('DESSERT', teamIds));

            eventResultCtrl.guestCourseTotalResult = initGuestCourseTotal(eventResultCtrl.guestCourseResults);

            $scope.guestCourseResults = eventResultCtrl.guestCourseResults;
        }

        $scope.$watch('guestCourseResults', function () {
            eventResultCtrl.guestCourseTotalResult = initGuestCourseTotal(eventResultCtrl.guestCourseResults);
        }, true);

        function initGuestCourseResult(courseType, teamIds) {
            var aCourseBattle = $filter("filter")(courseBattleResults, {courseType: courseType})[0];
            var guestCoureResult = {
                showResult: false,
                courseType: courseType,
                teamLeft: findCourseResult(teamIds[0], aCourseBattle),
                teamRight: findCourseResult(teamIds[1], aCourseBattle)
            };
            if (guestCoureResult.teamLeft && guestCoureResult.teamRight) {
                if (guestCoureResult.teamLeft.totalGuestVotes > guestCoureResult.teamRight.totalGuestVotes) {
                    guestCoureResult.teamLeft.points = 10;
                    guestCoureResult.teamRight.points = 0;
                    guestCoureResult.teamLeft.winner = true;
                } else if (guestCoureResult.teamRight.totalGuestVotes > guestCoureResult.teamLeft.totalGuestVotes) {
                    guestCoureResult.teamRight.points = 10;
                    guestCoureResult.teamLeft.points = 0;
                    guestCoureResult.teamRight.winner = true;
                } else {
                    // untentschieden
                    guestCoureResult.teamLeft.points = 0;
                    guestCoureResult.teamRight.points = 0;
                }
            }
            return  guestCoureResult
        }


        function initGuestCourseTotal(guestCourseResults) {
            var guestCourseTotalResult = {
                teamLeft: {totalPoints: 0, totalGuestVotes: 0, winner: false},
                teamRight: {totalPoints: 0, totalGuestVotes: 0, winner: false}
            };
            angular.forEach(guestCourseResults, function (guestCourseResult) {
                if (guestCourseResult.teamLeft && guestCourseResult.teamRight && guestCourseResult.showResult) {
                    guestCourseTotalResult.teamLeft.totalPoints += guestCourseResult.teamLeft.points;
                    guestCourseTotalResult.teamRight.totalPoints += guestCourseResult.teamRight.points;
                    guestCourseTotalResult.teamLeft.totalGuestVotes += guestCourseResult.teamLeft.totalGuestVotes;
                    guestCourseTotalResult.teamRight.totalGuestVotes += guestCourseResult.teamRight.totalGuestVotes;
                }
            });
            if (guestCourseTotalResult.teamLeft.totalPoints > guestCourseTotalResult.teamRight.totalPoints) {
                guestCourseTotalResult.teamLeft.winner = true;
            } else if (guestCourseTotalResult.teamRight.totalPoints > guestCourseTotalResult.teamLeft.totalPoints) {
                guestCourseTotalResult.teamRight.winner = true;
            }
            return guestCourseTotalResult;
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

    })
;