angular.module('votingResultsModule')
    .controller('CourseBattleSelectionController', function (battles, $state) {
        this.battles = battles;

        this.selectedBattle = function (battle) {
            $state.go('votingresults.show', {battleId: battle.battleId})
        }
    });