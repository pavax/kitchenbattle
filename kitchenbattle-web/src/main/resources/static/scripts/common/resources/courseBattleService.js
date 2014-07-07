'use strict';

angular.module('commons.resources.courseBattle', [])
    .factory('courseBattleService', function ($http) {
        return {
            getAllCourseBattles: function () {
                return $http.get('api/course-battles/search/allBattles');
            },
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
            deleteCourseBattle: function (courseOneId, courseTwoId, courseType) {
                return $http.delete('api/course-battles/' + battleId);
            }

        }
    });