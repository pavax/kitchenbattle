'use strict';
angular.module('adminModule')
    .directive("courseForm", function () {
        return {
            restrict: "E",
            templateUrl: "scripts/admin/courses/courseForm.html",
            scope: {
                course: "=",
                teams: "="
            }
        };
    });