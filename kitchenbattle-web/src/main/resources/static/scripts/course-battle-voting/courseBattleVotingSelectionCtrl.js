'use strict';

angular.module('courseBattleVotingModule')
    .controller('CourseBattleVotingSelectionController', function (battles, $state) {
        this.battles = battles;
        this.selectedBattle = function (battle) {
            $state.go('courseBattleVoting.vote', {battleId: battle.battleId})
        }
    });