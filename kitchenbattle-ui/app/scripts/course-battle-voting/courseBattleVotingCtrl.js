'use strict';

angular.module('courseBattleVotingModule')
    .controller('CourseBattleVotingCtrl', function ($scope, $state, battle, courseOne, courseTwo, courseBattleVoteService, $timeout, $modal) {

        var courseBattleVotingCtrl = this;

        this.battle = battle;

        this.courseOne = courseOne;

        this.courseTwo = courseTwo;

        this.voteRequestInProgress = false;

        this.voteCourse = function (course) {
            courseBattleVotingCtrl.voteRequestInProgress = true;
            courseBattleVoteService.voteCourse(course.courseId, this.battle.battleId)
                .then(function () {
                    var modalInstance = $modal.open({
                        templateUrl: 'scripts/course-battle-voting/voteConfirmationModal.html'
                    });
                    $timeout(function () {
                        modalInstance.close();
                        courseBattleVotingCtrl.voteRequestInProgress = false;
                    }, 1200);
                }, function (errorResponse) {
                    if (errorResponse.status === 409) {
                        window.alert('Diese Abstimmung wurde beendet');
                        $state.go('courseBattleVoting.battles.master', {eventId: battle.eventId});
                    } else {
                        window.alert('Ooops...Es ist ein Fehler aufgetreten: ' + errorResponse.status);
                    }
                    courseBattleVotingCtrl.voteRequestInProgress = false;
                });
        };
    });