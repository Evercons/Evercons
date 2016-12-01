anthapuModule.requires.push("com.anthapu.example.ionic.controllers.fpassword");

anthapuModule.config(function($stateProvider) {
    $stateProvider.state('forgot-password', {
        url: '/forgot-password',
        templateUrl: 'forgot-password/forgot-password.view.html',
        controller: 'fPasswordCtrl',
        controllerAs: 'vm'
    });
});

angular.module('com.anthapu.example.ionic.controllers.fpassword', [])
    .controller('fPasswordCtrl', function($scope, $state, AlertService, AuthenticationService) {
        var vm = this;
        vm.resetPassword = resetPassword;

        function resetPassword(user) {
            console.log('resetPassword', user);
            AuthenticationService.resetPassword(user, authenticateCallback);
        };

        function authenticateCallback(response) {
            AlertService.showAlert("HTTP Response", response.status);
            if (response.success) {
                $location.path('/sign-in');
            } else {
                errorCallback(response);
            }
        }

        function errorCallback(response) {
            AlertService.showAlert("HTTP Error Response", response.status);
        }
    });