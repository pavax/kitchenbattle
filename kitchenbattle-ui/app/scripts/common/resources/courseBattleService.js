'use strict';

angular.module('commons.resources.courseBattle', [])
    .factory('courseBattleService', function ($http) {
        return {
            getBattle: function (battleId) {
                return $http.get('api/course-battles/' + battleId);
            },
            createNewCourseBattle: function (courseOneId, courseTwoId, courseType) {
                return $http.post('api/course-battles',
                    {
                        courseOneId: courseOneId,
                        courseTwoId: courseTwoId,
                        courseType: courseType
                    }
                );
            },
            deleteCourseBattle: function (battleId) {
                return $http.delete('api/course-battles/' + battleId);
            },
            updateCourseBattleState: function (battleId, newState) {
                return $http(
                    {
                        method: 'PATCH',
                        url: 'api/course-battles/' + battleId + "/state",
                        data: {
                            nextState: newState
                        }
                    }
                );
            }
        }
    });