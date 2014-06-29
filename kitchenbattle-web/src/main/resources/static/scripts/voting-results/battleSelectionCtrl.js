angular.module('votingResultsModule')
    .controller('BattleSelectionController', function ($scope, battleService, $filter) {

        var battleSelection = this;

        battleService.getAllBattles().then(function (response) {
            battleSelection.battles = response.data;
        });
    });