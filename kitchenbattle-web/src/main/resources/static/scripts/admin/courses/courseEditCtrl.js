'use strict';
angular.module('adminModule')
    .controller('CourseEditController', function ($scope, $modalInstance, course, possibleTeams, courseService, upload) {

        $scope.course = angular.copy(course);

        $scope.possibleTeams = possibleTeams;

        $scope.imageUpload = {
            variantName: undefined
        };

        $scope.ok = function () {
            courseService.updateCourse($scope.course.courseId, $scope.course.courseType, $scope.course.name, $scope.course.description)
                .success(function (data) {
                    $modalInstance.close();
                })
                .error(function (error) {
                    alert('Ooops' + error.message);
                })
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        function reloadCourseImages() {
            courseService.getCourse(course.courseId).then(function (successResponse) {
                $scope.course.courseVariants = successResponse.data.courseVariants;
            });
        }

        $scope.onUploadSuccess = function (response) {
            $scope.imageUpload.variantName = undefined;
            reloadCourseImages();
        };

        $scope.deleteImage = function (variantName) {
            courseService.deleteCourseImage(course.courseId, variantName)
                .success(function () {
                    reloadCourseImages();
                });
        };

        $scope.getUploadUrl = function (variantName) {
            return "api/courses/" + course.courseId + "/image/" + variantName;
        };

    });