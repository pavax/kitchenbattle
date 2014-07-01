'use strict';

angular.module('courseBattleResultModule')
    .controller('CourseBattleSelectionController', function (battles, $state) {
        this.battles = battles;

        this.selectedBattle = function (battle) {
            $state.go('courseBattleResults.show', {battleId: battle.battleId})
        }
    });