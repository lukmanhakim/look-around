<?php
	
	require "../configuration/database.php";

	$query = "SELECT * FROM event";
	$result = mysqli_query($connection, $query);
	$num_rows = mysqli_num_rows($result);
	$data = null;
	if($num_rows > 0){
		while($row = mysqli_fetch_assoc($result)){
			$data[] = array(
					"id" => $row['id'],
					"title" => $row['title'],
					"description" => $row['description'],
					"lat" => $row['lat'],
					"lng" => $row['lng'],
					"reference" => $row['reference_url']
				);
		}
	}
?>

<!DOCTYPE html>
<html>
	<head>
    	<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    	<meta charset="utf-8">
    	<title>Look Around</title>
    	<style>
	      	/* Always set the map height explicitly to define the size of the div
	       	 * element that contains the map. */
	      	#map {
	        	height: 100%;
	      	}
	      	/* Optional: Makes the sample page fill the window. */
	      	html, body {
	        	height: 100%;
	        	margin: 0;
	        	padding: 0;
	      	}
		</style>
  	</head>
  	<body>
    	<div id="map"></div>
		<form id="form" method="post" action="../api/save.php" style="margin:18px 0px 18px 18px;">
			<center><h2>Add new Event</h2></center>
  			<table cellpadding="5">
				<tr>
					<td>Title</td>
					<td><input type="text" name="title"/></td>
				</tr>
  				<tr>
  					<td>Description</td>
  					<td><input type="text" name="description"/></td>
				</tr>
  				<tr>
  					<td>Reference</td>
  					<td><input type="text" name="reference"/></td>
				</tr>
         		<tr>
         			<td>	
						<input id="txtLng" type="hidden" name="lng"/>
						<input id="txtLat" type="hidden" name="lat"/>
         			</td>
         			<td align="right"><input type="submit" name="save" value="Save"/></td>
     			</tr>
  			</table>
	 	</form>
    	<script type="text/javascript">

			var events = <?php echo json_encode($data); ?>;
			var map;
			var newEventMarker;
			var newEventForm;

			function initMap() {


				map = new google.maps.Map(document.getElementById('map'), {
	    			zoom: 17,
	    			center: {lat: -25.363882, lng: 131.044922 }
	  			});
				
				if(events){
					for(var i = 0; i < events.length; i++){
			            var eventPosition = {
			            	lat : parseFloat(events[i].lat),
			            	lng : parseFloat(events[i].lng)
			            }

	        			var eventMarker = new google.maps.Marker({
	          				position: eventPosition,
	          				map: map,
	    					animation: google.maps.Animation.DROP,
	          				title: events[i].title,
	          				icon: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png'
	        			});

	 					infowindow = new google.maps.InfoWindow();

						eventMarker.content = '<div id="content">'+
				            '<div id="siteNotice">'+
				            '</div>'+
				            '<h1 id="firstHeading" class="firstHeading">'+ events[i].title +'</h1>'+
				            '<div id="bodyContent">'+
				            '<p>' + events[i].description + '</p>'+
				            '<p>Reference : <a href="' + events[i].reference + '" target="blank">' +
				            events[i].reference +
				            '</div>'+
				            '</div>';

	        			eventMarker.addListener('click', function() {
	        				infowindow.setContent(this.content);
							infowindow.open(map, this);
	        			});
					}	
				}
				

				// Try HTML5 geolocation.
				if (navigator.geolocation) {
					navigator.geolocation.getCurrentPosition(function(position) {
						var pos = {
							lat: position.coords.latitude,
							lng: position.coords.longitude
						};
						map.setCenter(pos);
      				}, function() {
            			handleLocationError(true, infoWindow, map.getCenter());
          			});
        		} else {
          			// Browser doesn't support Geolocation
          			handleLocationError(false, infoWindow, map.getCenter());
        		}
				
				newEventForm = new google.maps.InfoWindow({
          			content: document.getElementById('form')
        		})
				
				newEventMarker = new google.maps.Marker();



	  			map.addListener('click', function(e) {

	  				if(newEventMarker.getPosition()){
						newEventMarker.setPosition(e.latLng);
	  				} else {
	  					newEventMarker = new google.maps.Marker({
							position: e.latLng,
							map: map,
							draggable: true
						});
						newEventMarker.addListener('click', function() {
							newEventForm.open(map, newEventMarker);
    					});
	  				}

					document.getElementById('txtLat').value = e.latLng.lat();
					document.getElementById('txtLng').value = e.latLng.lng();
	  				map.panTo(e.latLng);
	  			});
			}
		</script>
    	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDBEasaZl2x-G8CeB7fD_MqHlckdN2SGW4&callback=initMap">
		</script>
	</body>
</html>