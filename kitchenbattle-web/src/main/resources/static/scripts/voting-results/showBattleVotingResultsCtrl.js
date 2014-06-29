angular.module('votingResultsModule')
    .controller('ShowBattleVotingResultsController', function ($scope, battleId, votingResultService, courseService, $timeout) {

        var showBattleVotingResult = this;

        function init() {
            votingResultService.getVotingResultForBattle(battleId).success(function (votingResult) {
                showBattleVotingResult.votingResult = votingResult;
                var totalGuestVotes = votingResult.totalGuestVotes;
                showBattleVotingResult.votingResult.courseOne.percentGuestCount = votingResult.courseOne.totalGuestVotes * 100 / totalGuestVotes;
                showBattleVotingResult.votingResult.courseOne.imageUrl = courseService.getCourseImageUrl(showBattleVotingResult.votingResult.courseOne.courseId);

                showBattleVotingResult.votingResult.courseTwo.percentGuestCount = votingResult.courseTwo.totalGuestVotes * 100 / totalGuestVotes;
                showBattleVotingResult.votingResult.courseTwo.imageUrl = courseService.getCourseImageUrl(showBattleVotingResult.votingResult.courseTwo.courseId);

                showBattleVotingResult.autoRefresh = votingResult.battleOpen;
            });


        }

        init();

        this.autoRefresh = false;

        function startInterval() {
            var timer = $timeout(function () {
                if (showBattleVotingResult.autoRefresh) {
                    init();
                }
                startInterval();
            }, 2000);
            $scope.$on('$destroy', function () {
                $timeout.cancel(timer);
            });
        }

        startInterval();
    })
;