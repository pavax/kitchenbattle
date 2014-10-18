'use strict';

angular.module('adminModule')
    .controller('CourseBattleResultController', function ($scope, selectedEvent, battleId, courseBattleSearchService, courseService, Fullscreen, hotkeys, $interval) {

        var courseBattleResultCtrl = this;

        this.selectedEvent = selectedEvent;

        this.anonymousVoting = false;

        this.fullscreen = Fullscreen;

        this.updateInterval = 2000;

        this.autoRefreshEnabled = false;

        hotkeys.bindTo($scope)
            .add({
                combo: 'shift+a',
                description: 'Toggle Anonymous Course Battle Voting ',
                callback: function () {
                    courseBattleResultCtrl.toggleAnonymousVoting();
                }
            });

        hotkeys.bindTo($scope)
            .add({
                combo: 'shift+right',
                description: 'Increase Update Interval',
                callback: function () {
                    courseBattleResultCtrl.updateInterval = courseBattleResultCtrl.updateInterval + 500;
                }
            });

        hotkeys.bindTo($scope)
            .add({
                combo: 'shift+left',
                description: 'Decrease Update Interval',
                callback: function () {
                    courseBattleResultCtrl.updateInterval = courseBattleResultCtrl.updateInterval - 500;
                }
            });

        this.toggleAnonymousVoting = function () {
            courseBattleResultCtrl.anonymousVoting = !courseBattleResultCtrl.anonymousVoting;
        };

        function receiveBattleResults() {
            return courseBattleSearchService.findCourseBattleResults(battleId)
                .then(function (successResponse) {
                    var votingResult = successResponse.data;
                    courseBattleResultCtrl.votingResult = votingResult;
                    var totalGuestVotes = votingResult.totalGuestVotes;
                    courseBattleResultCtrl.votingResult.courseOne.percentGuestCount = votingResult.courseOne.totalGuestVotes * 100 / totalGuestVotes;
                    courseBattleResultCtrl.votingResult.courseTwo.percentGuestCount = votingResult.courseTwo.totalGuestVotes * 100 / totalGuestVotes
                    if (courseBattleResultCtrl.votingResult.courseOne.courseVariants.length > 0) {
                        courseBattleResultCtrl.votingResult.courseOne.imageUrl = courseBattleResultCtrl.votingResult.courseOne.courseVariants[0].imageUrl;
                    }
                    if (courseBattleResultCtrl.votingResult.courseTwo.courseVariants.length > 0) {
                        courseBattleResultCtrl.votingResult.courseTwo.imageUrl = courseBattleResultCtrl.votingResult.courseTwo.courseVariants[0].imageUrl;
                    }
                    courseBattleResultCtrl.autoRefreshEnabled = votingResult.state === 'VOTING_IN_PROGRESS';
                    return successResponse;
                });
        }

        receiveBattleResults();

        this.goFullscreen = function () {

            if (Fullscreen.isEnabled())
                Fullscreen.cancel();
            else
                Fullscreen.all();

            // Set Fullscreen to a specific element (bad practice)
            // Fullscreen.enable( document.getElementById('img') )

        };

        $scope.$watch(function () {
            return courseBattleResultCtrl.updateInterval;
        }, function () {
            startInterval();
        });

        var timerPromise;

        function startInterval() {
            $interval.cancel(timerPromise);
            if (courseBattleResultCtrl.updateInterval) {
                timerPromise = $interval(receiveBattleResults, courseBattleResultCtrl.updateInterval);
            }
            $scope.$on('$destroy', function () {
                $interval.cancel(timerPromise);
            });
        }

    })
;