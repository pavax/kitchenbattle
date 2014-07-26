'use strict';

angular.module('courseBattleVotingModule')
    .controller('EventSelectionController', function (events, $state) {

        this.events = events;

        this.selectEvent = function (selectedEventId) {
            $state.go("courseBattleVoting.battles.master", {eventId: selectedEventId})
        }

    });