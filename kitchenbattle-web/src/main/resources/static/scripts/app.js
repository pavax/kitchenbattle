'use strict';

var app = angular.module('kitchenbattleWebApp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngTouch',
    'ui.router',
    'ui.bootstrap',
    'lr.upload',
    'commonsModule',
    'courseBattleVotingModule',
    'battleResultModule',
    'adminModule'
]);

app.config(function ($stateProvider, $urlRouterProvider) {
    //
    // For any unmatched url, redirect to /state1
    //$urlRouterProvider.otherwise("/votingresults");

});

app.config(function ($provide) {
    $provide.decorator('$state', function ($delegate, $stateParams) {
        $delegate.forceReload = function () {
            return $delegate.go($delegate.current, $stateParams, {
                reload: true,
                inherit: false,
                notify: true
            });
        };
        return $delegate;
    });
});

app.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.headers.patch = {
        'Content-Type': 'application/json;charset=utf-8'
    }
}]);

app.run(function ($rootScope, $state, $stateParams) {
        // It's very handy to add references to $state and $stateParams to the $rootScope
        // so that you can access them from any scope within your applications.For example,
        // <li ui-sref-active="active }"> will set the <li> // to active whenever
        // 'contacts.list' or one of its decendents is active.
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
    }
);
