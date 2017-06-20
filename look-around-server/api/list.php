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
	
	header('Content-Type: application/json');
	echo json_encode($data);
?>