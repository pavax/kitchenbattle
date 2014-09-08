'use strict';
angular.module('adminModule')
    .controller('AdminController', function ($rootScope, $scope, $state, events, selectedEventId, selectedEvent, $modal, eventsService) {

        this.events = events;

        var adminCtrl = this;

        this.selectedEventId = selectedEventId;

        this.selectedEvent = selectedEvent;

        this.showSelectionDropdown = selectedEvent == null;

        $rootScope.$broadcast('admin.selectedEvent', selectedEventId);

        this.selectEvent = function (selectedEventId) {
            $state.go($state.current.name, {eventId: selectedEventId})
        };

        this.deleteEvent = function (selectedEventId) {
            eventsService.deleteEvent(selectedEventId)
                .then(function () {
                    $state.go($state.current.name, {eventId: null})
                }, function (errorResponse) {
                    alert("Ooops" + errorResponse.data.message);
                });
        };

        this.editEventModal = function () {
            openEventModalInstance(adminCtrl.selectedEvent).result
                .then(function (eventFromModal) {
                    eventsService.updateEvent({eventId: eventFromModal.eventId, name: eventFromModal.eventName, eventDate: eventFromModal.eventDate})
                        .then(function () {
                            $state.forceReload();
                        }, function (errorResponse) {
                            alert("Ooops" + errorResponse.data.message);
                        });
                });
        };

        this.createNewEventModal = function () {
            openEventModalInstance(undefined).result
                .then(function (eventFromModal) {
                    eventsService.createEvent(eventFromModal.eventName, eventFromModal.eventDate)
                        .then(function (successResonse) {
                            var savedEventId = successResonse.data;
                            $state.go($state.current.name, {eventId: savedEventId})
                        }, function (errorResponse) {
                            alert("Ooops" + errorResponse.data.message);
                        });
                });
        };

        function openEventModalInstance(selectedEvent) {
            return $modal.open({
                templateUrl: 'scripts/admin/eventModal.html',
                controller: EventModalInstanceCtrl,
                resolve: {
                    selectedEvent: function () {
                        return selectedEvent;
                    }
                }
            });
        }

        var EventModalInstanceCtrl = function ($scope, $modalInstance, $filter, selectedEvent) {
            if (selectedEvent) {
                $scope.newEvent = {
                    eventId: selectedEvent.eventId,
                    eventName: selectedEvent.name,
                    eventDate: selectedEvent.eventDate
                };
            } else {
                $scope.newEvent = {
                    eventName: undefined,
                    eventDate: undefined
                };
            }
            $scope.ok = function () {
                $scope.newEvent.eventDate = $filter('date')($scope.newEvent.eventDate, "yyyy-MM-dd");
                $modalInstance.close($scope.newEvent);
            };
            $scope.cancel = function () {
                $modalInstance.dismiss();
            };
        };
    });