angular.module('votingModule')
    .controller('CourseBattleVotingSelectionController', function (battles, $state) {
        this.battles = battles;
        this.selectedBattle = function (battle) {
            $state.go('voting.vote', {battleId: battle.battleId})
        }
    });