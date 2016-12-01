anthapuModule.requires.push("com.anthapu.example.ionic.controllers.MapCtrl");

anthapuModule.config(function($stateProvider) {
    $stateProvider.state('app.map', {
        url: '/map',
        views: {
            'menuContent': {
                templateUrl: 'map/map.view.html',
                controller: 'MapCtrl',
                controllerAs: 'vm',
            }
        },
        params: {
            logData: null
        }
    });
});

angular.module('com.anthapu.example.ionic.controllers.MapCtrl', [])
    .controller('MapCtrl', function($scope, $ionicLoading, $compile, $state) {
        var vm = this;
        vm.map = null;
        vm.centerOnMe = centerOnMe;
        vm.clickTest = clickTest;
        vm.logData = $state.params.logData;
        vm.plotPoint = plotPoint;
        var markers = [];
        var currentLoc = null;

        function initialize() {
            var map = new google.maps.Map(document.getElementById("map"));
            map.setZoom(16);
            map.setMapTypeId(google.maps.MapTypeId.ROADMAP);
            vm.map = map;

            // Create the search box and link it to the UI element.
            var input = document.getElementById('pac-input');
            var searchBox = new google.maps.places.SearchBox(input);
            vm.map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

            // Bias the SearchBox results towards current map's viewport.
            vm.map.addListener('bounds_changed', function() {
                searchBox.setBounds(vm.map.getBounds());
            });

            // Listen for the event fired when the user selects a prediction and retrieve
            // more details for that place.
            searchBox.addListener('places_changed', function() {
                var places = searchBox.getPlaces();

                if (places.length == 0) {
                    return;
                }

                // Clear out the old markers.
                markers.forEach(function(marker) {
                    marker.setMap(null);
                });
                markers = [];

                // For each place, get the icon, name and location.
                var bounds = new google.maps.LatLngBounds();
                places.forEach(function(place) {
                    if (!place.geometry) {
                        console.log("Returned place contains no geometry");
                        return;
                    }
                    /*                    var icon = {
                                            url: place.icon,
                                            size: new google.maps.Size(71, 71),
                                            origin: new google.maps.Point(0, 0),
                                            anchor: new google.maps.Point(17, 34),
                                            scaledSize: new google.maps.Size(25, 25)
                                        };*/

                    // Create a marker for each place.
                    markers.push(new google.maps.Marker({
                        map: vm.map,
                        //                        icon: icon,
                        title: place.name,
                        position: place.geometry.location
                    }));

                    if (place.geometry.viewport) {
                        // Only geocodes have viewport.
                        bounds.union(place.geometry.viewport);
                    } else {
                        bounds.extend(place.geometry.location);
                    }
                });
                vm.map.fitBounds(bounds);
            });

            //initializeDrawTools();
            // centerOnMe();
            vm.map.setCenter(new google.maps.LatLng(12.974995499999999, 77.64702439999999));
        }

        // Initialize the map
        ionic.Platform.ready(initialize);

        function centerOnMe() {
            if (!vm.map) {
                return;
            }

            $ionicLoading.show({
                content: 'Getting current location...',
                showBackdrop: false
            });

            navigator.geolocation.getCurrentPosition(
                function(pos) {
                    console.log(pos.coords.latitude + "---" + pos.coords.longitude);
                    plotPoint(pos.coords.latitude, pos.coords.longitude);
                },
                function(error) {
                    alert('Unable to get location: ' + error.message);
                    $ionicLoading.hide();
                });
        }

        function clickTest() {
            alert('Example of infowindow with ng-click');
        }

        function plotPoint(lat, long) {
            var latLong = new google.maps.LatLng(lat, long);
            vm.map.setCenter(latLong);
            $ionicLoading.hide();
            var marker = new google.maps.Marker({
                position: latLong,
                map: vm.map
            });
            markers.push(marker);
            plotroute();
        }

        /*            function plotPoint(pos) {
                        var marker = new google.maps.Marker({
                            position: new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude),
                            map: vm.map
                        });
                        markers.push(marker);
                    }
        */

        function plotroute() {
            
            var latlng = getPoint(vm.logData[0]);
            var myOptions = { zoom: 9, center: latlng, mapTypeId: google.maps.MapTypeId.TERRAIN };
            var rendererOptions = { map: vm.map };
            directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);
            var point1 = getPoint(vm.logData[1]);
            var point2 = getPoint(vm.logData[2]);
            var point3 = getPoint(vm.logData[3]);
            var point4 = getPoint(vm.logData[4]);
            var point5 = getPoint(vm.logData[5])
            var wps = [{ location: point1 }, { location: point2 }, { location: point3 }, { location: point4 }];
            // waypoints plots points 
            var request = { origin: latlng, destination: point5, waypoints: wps, travelMode: google.maps.DirectionsTravelMode.DRIVING };
            //var request = { origin: org, destination: dest, travelMode: google.maps.DirectionsTravelMode.DRIVING };
            directionsService = new google.maps.DirectionsService();
            directionsService.route(request, function(response, status) {
                if (status == google.maps.DirectionsStatus.OK) { directionsDisplay.setDirections(response); } else
                    alert('failed to get directions');
            });
        }

        function getPoint(point) {
            return new google.maps.LatLng(point.latitude, point.longitude);
        }
/*        function plotroute() {
            var latlng = new google.maps.LatLng(-33.897, 150.099);
            var myOptions = { zoom: 9, center: latlng, mapTypeId: google.maps.MapTypeId.TERRAIN };
            //map = new google.maps.Map(document.getElementById("map"), myOptions);
            var rendererOptions = { map: vm.map };
            directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);
            var point1 = new google.maps.LatLng(-33.8975098545041, 151.09962701797485);
            var point2 = new google.maps.LatLng(-33.8584421519279, 151.0693073272705);
            var point3 = new google.maps.LatLng(-33.87312358690301, 151.99952697753906);
            var point4 = new google.maps.LatLng(-33.84525521656404, 151.0421848297119);
            var wps = [{ location: point1 }, { location: point2 }, { location: point4 }];
            var org = new google.maps.LatLng(-33.89192157947345, 151.13604068756104);
            var dest = new google.maps.LatLng(-33.69727974097957, 150.29047966003418);
            // waypoints plots points 
            //var request = { origin: org, destination: dest, waypoints: wps, travelMode: google.maps.DirectionsTravelMode.DRIVING };
            var request = { origin: org, destination: dest, travelMode: google.maps.DirectionsTravelMode.DRIVING };
            directionsService = new google.maps.DirectionsService();
            directionsService.route(request, function(response, status) {
                if (status == google.maps.DirectionsStatus.OK) { directionsDisplay.setDirections(response); } else
                    alert('failed to get directions');
            });
        }
*/
        function initializeDrawTools() {
            var drawingManager = new google.maps.drawing.DrawingManager({
                drawingMode: google.maps.drawing.OverlayType.MARKER,
                drawingControl: true,
                drawingControlOptions: {
                    position: google.maps.ControlPosition.TOP_CENTER,
                    drawingModes: ['marker', 'circle', 'polygon', 'polyline', 'rectangle']
                },
                markerOptions: { icon: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png' },
                circleOptions: {
                    fillColor: '#ffff00',
                    fillOpacity: 1,
                    strokeWeight: 5,
                    clickable: false,
                    editable: true,
                    zIndex: 1
                }
            });
            drawingManager.setMap(vm.map);
        }

    });
