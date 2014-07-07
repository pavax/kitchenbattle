'use strict';
angular.module('adminModule')
    .controller('TeamController', function ($scope, teams, $state, teamService) {

        var teamCtrl = this;
        this.teams = teams;
        this.showCreateNewTeamForm = false;
        this.toogleNewTeamForm = function () {
            teamCtrl.showCreateNewTeamForm = !teamCtrl.showCreateNewTeamForm;
            teamCtrl.newTeam = {};
        };

        this.saveTeam = function () {
            if ($scope.createNewTeamForm.$valid) {
                teamService.createTeam(teamCtrl.newTeam.name, teamCtrl.newTeam.description)
                    .success(function () {
                        $state.forceReload();
                    })
                    .error(function () {
                        alert("Oops an Error occured: " + error);
                    });
            }
        };

        this.deleteTeam = function (teamId) {
            teamService.deleteTeam(teamId)
                .success(function () {
                    $state.forceReload();
                })
                .error(function (error) {
                    alert("Oops an Error occured: " + error);
                });
        };

    });