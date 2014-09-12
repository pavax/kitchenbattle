angular.module('commons.directives.disableNgAnimate', ['ngAnimate']);
app.directive('disableNgAnimate', ['$animate', function ($animate) {
    return {
        restrict: 'A',
        link: function (scope, element) {
            $animate.enabled(false, element);
        }
    };
}]);