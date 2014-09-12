'use strict';
angular.module('adminModule')
    .controller('TeamController', function ($scope, teams, $state, teamService, selectedEventId) {

        var teamCtrl = this;
        this.teams = teams;
        this.showCreateNewTeamForm = false;
        this.toogleNewTeamForm = function () {
            $scope.$broadcast('show-errors-reset');
            teamCtrl.showCreateNewTeamForm = !teamCtrl.showCreateNewTeamForm;
            teamCtrl.newTeam = {};
        };

        this.saveTeam = function () {
            $scope.$broadcast('show-errors-check-validity');
            if ($scope.createNewTeamForm.$valid) {
                teamService.createTeam(teamCtrl.newTeam.name, teamCtrl.newTeam.description, selectedEventId)
                    .success(function () {
                        $state.forceReload();
                    })
                    .error(function () {
                        alert("Oops an Error occured: " + error);
                    });
            }
        };

        this.deleteTeam = function (teamId) {
            if (window.confirm("Sure?")) {
                teamService.deleteTeam(teamId)
                    .success(function () {
                        $state.forceReload();
                    })
                    .error(function (error) {
                        alert("Oops an Error occured: " + error);
                    });
            }
        };

    });