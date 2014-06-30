angular.module('commons.filters.courseType', [])
    .filter('courseType', function () {
        return function (input) {
            switch (input) {
                case 'STARTER':
                    return 'Vorspeise';
                case 'MAIN':
                    return 'Hauptgang';
                case 'DESSERT':
                    return 'Dessert';
                default:
                    return undefined;
            }
        };
    });