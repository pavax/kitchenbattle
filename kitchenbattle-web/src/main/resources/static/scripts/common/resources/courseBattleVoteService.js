'use strict';

angular.module('commons.resources.courseBattleVote', [])
    .factory('courseBattleVoteService', function ($http) {
        return {
            voteCourse: function (courseId, battleId) {
                return $http.post('api/course-battle-votes', {
                        votedCourseId: courseId,
                        battleId: battleId
                    }
                );
            }
        }
    });