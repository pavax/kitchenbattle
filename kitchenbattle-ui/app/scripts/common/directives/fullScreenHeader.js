angular.module('commons.directives.fullScreenHeader', ['FBAngular']);
app.directive('fullScreenHeader', function (Fullscreen) {
    return {
        restrict: 'A',
        scope: {
            event: '='
        },
        templateUrl: 'scripts/common/directives/fullScreenHeader.html',
        transclude: true,
        link: function (scope, element) {
            scope.fullScreen = Fullscreen;
            scope.enabled = Fullscreen.isEnabled();

            scope.$watch('fullScreen', function () {
                console.log("changed");
            });
        }
    };
});