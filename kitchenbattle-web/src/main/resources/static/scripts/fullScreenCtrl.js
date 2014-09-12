'use strict';

angular.module('kitchenbattleWebApp')
    .controller('fullscreenCtrl', function (Fullscreen) {
        this.goFullscreen = function () {
            // Set Fullscreen to a specific element (bad practice)
            Fullscreen.enable(document.getElementById('fullscreen'))
        };
    });