'use strict';

angular.module('adminModule')
    .controller('CourseBattleResultController', function ($scope, battleId, courseBattleSearchService, courseService, $timeout, Fullscreen) {

        var courseBattleResultCtrl = this;

        function init() {
            courseBattleSearchService.findCourseBattleResults(battleId)
                .then(function (successResponse) {
                    var votingResult = successResponse.data;
                    courseBattleResultCtrl.votingResult = votingResult;
                    var totalGuestVotes = votingResult.totalGuestVotes;
                    courseBattleResultCtrl.votingResult.courseOne.percentGuestCount = votingResult.courseOne.totalGuestVotes * 100 / totalGuestVotes;
                    courseBattleResultCtrl.votingResult.courseOne.imageUrl = courseBattleResultCtrl.votingResult.courseOne.courseVariants[0].imageUrl;

                    courseBattleResultCtrl.votingResult.courseTwo.percentGuestCount = votingResult.courseTwo.totalGuestVotes * 100 / totalGuestVotes;
                    courseBattleResultCtrl.votingResult.courseTwo.imageUrl = courseBattleResultCtrl.votingResult.courseTwo.courseVariants[0].imageUrl;

                    courseBattleResultCtrl.autoRefresh = votingResult.state === 'VOTING_IN_PROGRESS';
                });
        }

        init();

        this.goFullscreen = function () {

            if (Fullscreen.isEnabled())
                Fullscreen.cancel();
            else
                Fullscreen.all();

            // Set Fullscreen to a specific element (bad practice)
            // Fullscreen.enable( document.getElementById('img') )

        }

        this.autoRefresh = false;

        function startInterval() {
            var timer = $timeout(function () {
                if (courseBattleResultCtrl.autoRefresh) {
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