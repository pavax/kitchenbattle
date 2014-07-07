'use strict';

angular.module('courseBattleResultModule')
    .controller('ShowCourseBattleVotingResultsController', function ($scope, battleId, courseBattleResultService, courseService, $timeout) {

        var showCourseBattleVotingResult = this;

        function init() {
            courseBattleResultService.getResultForBattle(battleId).success(function (votingResult) {
                showCourseBattleVotingResult.votingResult = votingResult;
                var totalGuestVotes = votingResult.totalGuestVotes;
                showCourseBattleVotingResult.votingResult.courseOne.percentGuestCount = votingResult.courseOne.totalGuestVotes * 100 / totalGuestVotes;
                var courseOneVariant = "NORMAL";
                showCourseBattleVotingResult.votingResult.courseOne.imageUrl = courseService.getCourseImageUrl(showCourseBattleVotingResult.votingResult.courseOne.courseId, courseOneVariant);

                var courseTwoVariant = "NORMAL";
                showCourseBattleVotingResult.votingResult.courseTwo.percentGuestCount = votingResult.courseTwo.totalGuestVotes * 100 / totalGuestVotes;
                showCourseBattleVotingResult.votingResult.courseTwo.imageUrl = courseService.getCourseImageUrl(showCourseBattleVotingResult.votingResult.courseTwo.courseId,courseTwoVariant);

                showCourseBattleVotingResult.autoRefresh = votingResult.battleOpen;
            });
        }

        init();

        this.autoRefresh = false;

        function startInterval() {
            var timer = $timeout(function () {
                if (showCourseBattleVotingResult.autoRefresh) {
                    init();
                }
                startInterval();
            }, 500);
            $scope.$on('$destroy', function () {
                $timeout.cancel(timer);
            });
        }

        startInterval();
    })
;