'use strict';

angular.module('commonsModule',
    [
        'commons.resources.courseBattle',
        'commons.resources.courseBattleResult',
        'commons.resources.course',
        'commons.resources.courseBattleVote',
        'commons.filters.courseType',
        'commons.resources.juryTeamVote',
        'commons.resources.teams',
        'commons.resources.events',
        'commons.filters.courseBattleState',
        'commons.resources.currentUser',
        'commons.directives.disableNgAnimate',
        'commons.directives.showErrors',
        'commons.directives.fullScreenHeader'
    ]
);
