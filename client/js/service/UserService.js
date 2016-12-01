anthapuModule.requires.push("com.anthapu.example.ionic.services.UserService");

angular.module('com.anthapu.example.ionic.services.UserService', [])
    .factory('UserService', function($http) {
        return {
            GetAll: function() {
                return $http.get('/api/users').then(handleSuccess, handleError('Error getting all users'));
            },
            GetById: function(id) {
                return $http.get('/api/users/' + id).then(handleSuccess, handleError('Error getting user by id'));
            },
            GetByUsername: function(username) {
                return $http.get('/api/users/' + username).then(handleSuccess, handleError('Error getting user by username'));
            },
            Create: function(user) {
                return $http.post('/api/users', user).then(handleSuccess, handleError('Error creating user'));
            },
            Update: function(user) {
                return $http.put('/api/users/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
            },
            Delete: function(id) {
                return $http.delete('/api/users/' + id).then(handleSuccess, handleError('Error deleting user'));
            }
        }
        // private functions
        function handleSuccess(res) {
            return res.data;
        }

        function handleError(error) {
            return function() {
                return { success: false, message: error };
            };
        }
    });
