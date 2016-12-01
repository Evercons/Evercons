anthapuModule.requires.push("com.anthapu.example.ionic.controllers.HomeCtrl");

anthapuModule.config(function($stateProvider) {
    $stateProvider.state('app.home', {
        url: '/home',
        views: {
            'menuContent': {
                templateUrl: 'home/home.view.html',
                controller: 'HomeCtrl',
                controllerAs: 'vm',
            }
        }
    });
});

angular.module('com.anthapu.example.ionic.controllers.HomeCtrl', [])
    .controller('HomeCtrl', function($scope, $rootScope, $state, $http, AlertService, AuthenticationService) {
        var vm = this;
        vm.deleteDevice = deleteDevice;
        vm.user = $rootScope.currentUser;
        vm.deviceData = deviceData;

        function deleteDevice(deviveId) {
            console.log('deleteDevice', deviveId);
            AlertService.showConfirm('Delete Device', 'Are you sure you want to delete device?');
            // AuthenticationService.register(user, successCallback, errorCallback);
        };

        function deviceData(deviveId) {
            console.log('deviceData', deviveId);
            var input = {"deviceId":deviveId};
             var req = {
                    method: 'POST',
                    url: $rootScope.SERVER_URL + '/device',
                    headers : {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    data : { cmd: 'deviceData', data: JSON.stringify(input) }
                }
                $http(req).then(successCallback, errorCallback);
        };

        function successCallback(response) {
            console.log("HTTP Response", response.status);
            if (response.status == 200) {
                $state.go('app.map', {logData: response.data.logData});
            } else {
                errorCallback(response);
            }
        }

        function errorCallback(response) {
            AlertService.showAlert("HTTP Error Response", response.status);
        }
    });
