anthapuModule.requires.push("com.anthapu.example.ionic.controllers.SignInCtrl");

anthapuModule.config(function($stateProvider) {
    $stateProvider.state('signin', {
        url: '/sign-in',
        templateUrl: 'login/login.view.html',
        controller: 'SignInCtrl',
        controllerAs: 'vm'
    });
});

angular.module('com.anthapu.example.ionic.controllers.SignInCtrl', [])
    .controller('SignInCtrl', function($scope, $rootScope, $state, $location, AlertService, AuthenticationService) {
        var vm = this;
        vm.signIn = signIn;

        function signIn(user) {
            console.log('Sign-In', user);
            AuthenticationService.login(user, successCallback, errorCallback);
        };

        function successCallback(response) {
            console.log("HTTP Response", response.status);
            if (response.status == 200) {
                $rootScope.currentUser =  response.data;
                $state.go('app.home');
                //$location.path('/home');
            } else {
                errorCallback(response);
            }
        }

        function errorCallback(response) {
            AuthenticationService.clearCredentials();
            AlertService.showAlert("HTTP Error Response", response.status);
        }

        $scope.$on("$ionicView.afterEnter", function(event, data) {
            // handle event
            console.log("State Params: ", data);
        });

    });
