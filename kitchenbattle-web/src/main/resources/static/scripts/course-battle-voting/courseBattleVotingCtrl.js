'use strict';

angular.module('courseBattleVotingModule')
    .controller('CourseBattleVotingCtrl', function ($scope, battle, courseOne, courseTwo, courseBattleVoteService) {
        this.battle = battle;

        this.courseOne = courseOne;

        this.courseTwo = courseTwo;

        this.voteCourse = function (courseId) {
            courseBattleVoteService.voteCourse(courseId, this.battle.battleId)
                .success(function (data) {
                    alert("Danke");
                }).error(function (error) {
                    alert("Ooops...Es ist ein Fehler aufgetreten");
                });
        };
    });