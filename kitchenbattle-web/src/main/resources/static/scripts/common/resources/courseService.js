'use strict';

angular.module('commons.resources.course', [])
    .factory('courseService', function ($http) {
        function buildCourseImageUrl(courseId, variant) {
            var variantValue = variant.value || variant;
            return 'api/courses/' + courseId + '/image/' + variantValue;
        }

        function applyFurtherCourseProperties(course) {
            angular.forEach(course.courseVariants, function (variant) {
                variant.imageUrl = buildCourseImageUrl(course.courseId, variant);
            });
        }

        return {
            getCourse: function (courseId) {
                return $http.get('api/courses/' + courseId).then(function (successResponse) {
                    applyFurtherCourseProperties(successResponse.data);
                    return successResponse;
                });
            },
            findCoursesByTeam: function (teamId) {
                return $http.get('api/courses/find/byTeamId', {params: {teamId: teamId}}).then(function (successResponse) {
                    angular.forEach(successResponse.data, function (course) {
                        applyFurtherCourseProperties(course);
                    });
                    return successResponse;
                });
            },
            createCourse: function (teamId, courseType, courseName, description,eventId) {
                return $http.post('api/courses',
                    {
                        description: description,
                        courseType: courseType,
                        courseName: courseName,
                        teamId: teamId,
                        eventId: eventId
                    }
                );
            },
            updateCourse: function (courseId, courseType, courseName, description) {
                return $http(
                    {
                        method: 'PATCH',
                        url: 'api/courses/' + courseId,
                        data: {
                            description: description,
                            courseType: courseType,
                            name: courseName
                        }
                    });
            },
            deleteCourse: function (courseId) {
                return $http.delete('api/courses/' + courseId);
            },
            getCourseImageUrl: function (courseId, variant) {
                return buildCourseImageUrl(courseId, variant);
            },
            deleteCourseImage: function (courseId, variant) {
                var variantValue = variant.value || variant;
                return $http.delete('api/courses/' + courseId + '/image/' + variantValue);
            }
        }
    });