'use strict';

angular.module('commons.resources.course', [])
    .factory('courseService', function ($http) {
        return {
            getCourseImageUrl: function (courseId) {
                return 'api/courses/' + courseId + '/image';
            },
            getCourse: function (courseId) {
                return $http.get('api/courses/' + courseId);
            }
        }
    });