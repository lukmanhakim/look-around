<?php
	require "../configuration/database.php";
	if(isset($_POST["save"])){
		$title = $_POST["title"];
		$description = $_POST["description"];
		$lat = $_POST["lat"];
		$lng = $_POST["lng"];
		$reference = $_POST["reference"];
		$id_user = "1";

		$insert = 
			mysqli_query($connection, 
				"INSERT INTO event VALUES(null, '$title', '$description', '$lat', '$lng', '$reference', $id_user)");

		header('Location: ../web/');
	}
?>