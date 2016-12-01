anthapuModule.requires.push("com.anthapu.example.ionic.controllers.MenuCtrl");

anthapuModule.config(function($stateProvider) {
    $stateProvider.state('app', {
        url: '/app',
        templateUrl: 'menu/menu.view.html',
        controller: 'MenuCtrl',
        controllerAs: 'vm',
        abstract: true
    });
});

angular.module('com.anthapu.example.ionic.controllers.MenuCtrl', [])
    .controller('MenuCtrl', function($scope, $state) {
        var vm = this;
        vm.logout = logout;

        function logout() {
            console.log('logout clicked');
            $state.go('signin');
        };
    });
