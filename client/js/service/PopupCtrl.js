anthapuModule.requires.push("com.anthapu.example.ionic.services.AlertService");

angular.module('com.anthapu.example.ionic.services.AlertService', [])
    .factory('AlertService', function($ionicPopup) {
        return {
            showAlert: function(title, message) {
                var alertPopup = $ionicPopup.alert({
                    title: title,
                    template: message
                });
                alertPopup.then(function(res) {
                    console.log('alert Closed');
                });
            },
            showConfirm: function(title, message) {
                var confirmPopup = $ionicPopup.confirm({
                    title: title,
                    template: message
                });
                confirmPopup.then(function(res) {
                    if (res) {
                        console.log('You are sure');
                    } else {
                        console.log('You are not sure');
                    }
                });
            }
        }
    });
