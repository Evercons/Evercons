anthapuModule.requires.push("com.anthapu.example.ionic.controllers");

anthapuModule.config(function($stateProvider) {
    $stateProvider.state('signin', {
        url: '/sign-in',
        templateUrl: 'templates/sign-in.html',
        controller: 'SignInCtrl'
    });
});

angular.module('com.anthapu.example.ionic.controllers', ['com.anthapu.example.ionic.services.AlertService'])
    .controller('SignInCtrl', function($scope, $state, $http, AlertService) {
        $scope.signIn = function(user) {
            console.log('Sign-In', user);

            $http.get('http://192.168.21.62:8080/EvacafeNotificationWeb/userDeviceMapping').then(successCallback, errorCallback);
        };

        function successCallback(response) {
            AlertService.showAlert("HTTP Response", response);
        }

        function errorCallback(response) {
            AlertService.showAlert("HTTP Error Response", response);
        }
    });
