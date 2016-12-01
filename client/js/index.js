var anthapuModule = angular.module('anthapuApp', ['ionic'])

anthapuModule.config(function($stateProvider, $urlRouterProvider, $httpProvider, $ionicConfigProvider) {

    $httpProvider.defaults.headers.common = {};
    $httpProvider.defaults.headers.post = {};
    $httpProvider.defaults.headers.put = {};
    $httpProvider.defaults.headers.patch = {};

//    $ionicConfigProvider.scrolling.jsScrolling(false);

    $urlRouterProvider.otherwise('/sign-in');

    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];

    $httpProvider.interceptors.push(function($rootScope) {
        return {
            request: function(config) {
                $rootScope.$broadcast('loading:show')
                return config;
            },
            response: function(response) {
                $rootScope.$broadcast('loading:hide')
                return response;
            },
            responseError: function(response) {
                $rootScope.$broadcast('loading:hide')
                return response;
            }
        }
    });

});

anthapuModule.run(function($rootScope, $ionicLoading) {
    // Server configuration
    $rootScope.SERVER_URL='http://localhost:8080/everconServer';
    $rootScope.currentUser = null;
    //$rootScope.SERVER_URL='http://ec2-52-221-245-83.ap-southeast-1.compute.amazonaws.com:8080/everconServer';

    $rootScope
        .$on(
            'loading:show',
            function() {
                $ionicLoading
                    .show({
                        template: '<p>Loading...</p><ion-spinner icon="circles" class="spinner-energized"></ion-spinner>'
                    })
            })

    $rootScope.$on('loading:hide', function() {
        $ionicLoading.hide();
    });
});
