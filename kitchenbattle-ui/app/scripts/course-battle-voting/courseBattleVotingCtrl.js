'use strict';

angular.module('courseBattleVotingModule')
    .controller('ModalInstanceCtrl', function ($scope, $modalInstance, voteStatus) {

        $scope.voteStatus = voteStatus;
    })
    .controller('CourseBattleVotingCtrl', function ($scope, $state, battle, courseOne, courseTwo, courseBattleVoteService, $timeout, $modal) {

        var courseBattleVotingCtrl = this;

        this.battle = battle;

        this.courseOne = courseOne;

        this.courseTwo = courseTwo;

        this.voteStatus = {
            voteRequestInProgress: false
        };

        this.voteCourse = function (course) {
            courseBattleVotingCtrl.voteStatus.voteRequestInProgress = true;
            var modalInstance = $modal.open({
                templateUrl: 'scripts/course-battle-voting/voteConfirmationModal.html',
                controller: 'ModalInstanceCtrl',
                resolve: {
                    voteStatus: function () {
                        return courseBattleVotingCtrl.voteStatus;
                    }
                }
            });
            courseBattleVoteService.voteCourse(course.courseId, this.battle.battleId)
                .then(function () {
                    courseBattleVotingCtrl.voteStatus.voteRequestInProgress = false;
                    $timeout(function () {
                        modalInstance.close();
                    }, 2000);
                }, function (errorResponse) {
                    if (errorResponse.status === 409) {
                        window.alert('Diese Abstimmung wurde beendet');
                        $state.go('courseBattleVoting.battles.master', {eventId: battle.eventId});
                    } else {
                        window.alert('Ooops...Es ist ein Fehler aufgetreten: ' + errorResponse.status);
                    }
                    courseBattleVotingCtrl.voteStatus.voteRequestInProgress = false;
                });
        };
    });