'use strict';

angular.module('courseBattleVotingModule')
    .controller('CourseBattleSelectionController', function (battles, $state) {
        this.battles = battles;
        this.selectedBattle = function (battle) {
            $state.go('courseBattleVoting.battles.vote', {battleId: battle.battleId})
        }
    });