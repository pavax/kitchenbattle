angular.module('commons.filters.courseBattleState', [])
    .filter('courseBattleState', function () {
        return function (input) {
            switch (input) {
                case 'INITIALIZED':
                    return 'Initalisiert';
                case 'VOTING_IN_PROGRESS':
                    return 'Abstimmung läuft';
                case 'CLOSED':
                    return 'Beendet';
                default:
                    return undefined;
            }
        };
    });