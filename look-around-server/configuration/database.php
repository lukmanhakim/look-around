<?php	
	$host = "localhost";
	$username = "root";
	$password = "admin";
	$database = "look-around_dev";

	$connection = mysqli_connect($host, $username, $password);
	if (mysqli_connect_errno()) {
	    die(mysqli_connect_error());
	}

	$db_select = mysqli_select_db($connection, $database);
	if (mysqli_errno($connection)) {
	    die(mysqli_error($connection));
	}
?>