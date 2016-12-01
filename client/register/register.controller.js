anthapuModule.requires.push("com.anthapu.example.ionic.controllers.RegisterCtrl");

anthapuModule.config(function($stateProvider) {
    $stateProvider.state('register', {
        url: '/register',
        templateUrl: 'register/register.view.html',
        controller: 'RegisterCtrl',
        controllerAs: 'vm'
    });
});

angular.module('com.anthapu.example.ionic.controllers.RegisterCtrl', [])
    .controller('RegisterCtrl', function($scope, $state, $location, AlertService, AuthenticationService) {
        var vm = this;
        vm.register = register;

        function register(user) {
            console.log('Register', user);
            AuthenticationService.register(user, successCallback, errorCallback);
        };

        function successCallback(response) {
            AlertService.showAlert("HTTP Response", response.status);
            if (response.status==200) {
                $location.path('/sign-in');
            } else {
                errorCallback(response);
            }
        }

        function errorCallback(response) {
            AlertService.showAlert("HTTP Error Response", response.status);
        }
    });
