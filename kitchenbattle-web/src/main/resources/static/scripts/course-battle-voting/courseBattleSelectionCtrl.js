'use strict';

angular.module('courseBattleVotingModule')
    .controller('CourseBattleSelectionController', function (battles, $state, selectedEvent) {
        this.battles = battles;
        this.selectedEvent = selectedEvent;
        this.selectedBattle = function (battle) {
            $state.go('courseBattleVoting.battles.vote', {battleId: battle.battleId})
        }
    });