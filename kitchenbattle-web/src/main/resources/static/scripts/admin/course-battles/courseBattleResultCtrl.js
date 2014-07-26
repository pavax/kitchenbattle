'use strict';

angular.module('adminModule')
    .controller('CourseBattleResultController', function ($scope, battleId, courseBattleSearchService, courseService, $timeout) {

        var courseBattleResultCtrl = this;

        function init() {
            courseBattleSearchService.findCourseBattleResults(battleId).success(function (votingResult) {
                courseBattleResultCtrl.votingResult = votingResult;
                var totalGuestVotes = votingResult.totalGuestVotes;
                courseBattleResultCtrl.votingResult.courseOne.percentGuestCount = votingResult.courseOne.totalGuestVotes * 100 / totalGuestVotes;
                var courseOneVariant = "NORMAL";
                courseBattleResultCtrl.votingResult.courseOne.imageUrl = courseService.getCourseImageUrl(courseBattleResultCtrl.votingResult.courseOne.courseId, courseOneVariant);

                var courseTwoVariant = "NORMAL";
                courseBattleResultCtrl.votingResult.courseTwo.percentGuestCount = votingResult.courseTwo.totalGuestVotes * 100 / totalGuestVotes;
                courseBattleResultCtrl.votingResult.courseTwo.imageUrl = courseService.getCourseImageUrl(courseBattleResultCtrl.votingResult.courseTwo.courseId,courseTwoVariant);

                //courseBattleResultCtrl.autoRefresh = votingResult.battleOpen;
            });
        }

        init();

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