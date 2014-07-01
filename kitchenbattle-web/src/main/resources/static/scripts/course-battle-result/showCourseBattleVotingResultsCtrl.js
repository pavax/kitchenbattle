'use strict';

angular.module('courseBattleResultModule')
    .controller('ShowCourseBattleVotingResultsController', function ($scope, battleId, courseBattleResultSearchService, courseService, $timeout) {

        var showCourseBattleVotingResult = this;

        function init() {
            courseBattleResultSearchService.getResultForBattle(battleId).success(function (votingResult) {
                showCourseBattleVotingResult.votingResult = votingResult;
                var totalGuestVotes = votingResult.totalGuestVotes;
                showCourseBattleVotingResult.votingResult.courseOne.percentGuestCount = votingResult.courseOne.totalGuestVotes * 100 / totalGuestVotes;
                showCourseBattleVotingResult.votingResult.courseOne.imageUrl = courseService.getCourseImageUrl(showCourseBattleVotingResult.votingResult.courseOne.courseId);

                showCourseBattleVotingResult.votingResult.courseTwo.percentGuestCount = votingResult.courseTwo.totalGuestVotes * 100 / totalGuestVotes;
                showCourseBattleVotingResult.votingResult.courseTwo.imageUrl = courseService.getCourseImageUrl(showCourseBattleVotingResult.votingResult.courseTwo.courseId);

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