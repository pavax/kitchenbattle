angular.module('votingModule')
    .controller('CourseBattleVotingCtrl', function (battle, courseOne, courseTwo, courseVotingService, courseService) {
        this.battle = battle;
        this.courseOne = courseOne;
        this.courseOne.imageUrl = courseService.getCourseImageUrl(courseOne.courseId);
        this.courseTwo = courseTwo;
        this.courseTwo.imageUrl = courseService.getCourseImageUrl(courseTwo.courseId);
        this.voteCourse = function (courseId) {
            courseVotingService.voteCourse(courseId, this.battle.battleId)
                .success(function (data) {
                    alert("Danke");
                }).error(function (error) {
                    alert("Ooops...Es ist ein Fehler aufgetreten");
                });
        }
    });