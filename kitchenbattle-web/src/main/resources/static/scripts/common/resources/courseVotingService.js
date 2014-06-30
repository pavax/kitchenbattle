'use strict';

angular.module('commons.resources.courseVoting', [])
    .factory('courseVotingService', function ($http) {
        return {
            voteCourse: function (courseId, battleId) {
                return $http.post('api/votings', {
                        votedCourseId: courseId,
                        battleId: battleId
                    }
                );
            }
        }
    });