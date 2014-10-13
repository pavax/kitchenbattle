'use strict';

angular.module('commons.resources.courseBattleResult', [])
    .factory('courseBattleSearchService', function ($http) {

        function applyFurtherCourseProperties(course) {
            angular.forEach(course.courseVariants, function (variant) {
                variant.imageUrl = buildCourseImageUrl(course.courseId, variant);
            });
        }
        function buildCourseImageUrl(courseId, variant) {
            var variantValue = variant.value || variant;
            return 'api/courses/' + courseId + '/image/' + variantValue;
        }

        return {
            findCourseBattleResults: function (battleId) {
                return $http.get('api/course-battles-search/results/' + battleId).then(function (successResponse) {
                    applyFurtherCourseProperties(successResponse.data.courseOne);
                    applyFurtherCourseProperties(successResponse.data.courseTwo);
                    return successResponse;
                });
            },
            getAllCourseBattleResult: function () {
                return $http.get('api/course-battles-search/results');
            },
            findAllCourseBattlesWithDetails: function (eventId) {
                return $http.get('api/course-battles-search', {params: {eventId: eventId}});
            }
        }
    });