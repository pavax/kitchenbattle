'use strict';

angular.module('adminModule')
    .controller('CoursesController', function ($scope, teams, courses, selectedTeam, selectedEventId, courseService, $state, $modal) {
        this.teams = teams;
        this.courses = courses;

        var coursesCtrl = this;

        this.showCreateCourseForm = false;

        this.selectedTeam = selectedTeam || {};

        this.selectTeam = function (teamId) {
            $state.go('admin.courses', {teamId: teamId})
        };

        this.toogleNewCourseForm = function () {
            coursesCtrl.showCreateCourseForm = !coursesCtrl.showCreateCourseForm;
            coursesCtrl.newCourse = {};
        };

        this.saveCourse = function () {
            if ($scope.createNewCourseForm.$valid) {
                courseService.createCourse(
                    coursesCtrl.selectedTeam.teamId,
                    coursesCtrl.newCourse.courseType,
                    coursesCtrl.newCourse.name,
                    coursesCtrl.newCourse.description,
                    selectedEventId
                )
                    .success(function () {
                        $state.forceReload();
                    })
                    .error(function (error) {
                        alert("Oops an Error occured: " + error.data);
                    });
            }
        };

        this.editCourse = function (course) {
            var modalInstance = $modal.open({
                templateUrl: 'scripts/admin/courses/courseEditModal.html',
                controller: "CourseEditController",
                size: 'lg',
                resolve: {
                    course: function () {
                        return course;
                    }
                }
            });
            modalInstance.result
                .then(function (editedCourse) {
                    $state.forceReload();
                }, function () {

                });
        };

        this.deleteCourse = function (course) {
            courseService.deleteCourse(course.courseId)
                .success(function () {
                    $state.forceReload();
                })
                .error(function (error) {
                    alert('Ooops: ' + error)
                });
        };
    });