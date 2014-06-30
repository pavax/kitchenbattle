'use strict';

angular.module('commons.directives.courseBattles', ['commons.filters.courseType'])
    .directive("courseBattlesTable", function () {
        return {
            restrict: "E",
            templateUrl: "scripts/common/directives/courseBattlesTable.html",
            scope: {
                battles: "=",
                onSelect: "&"
            }
        };
    });