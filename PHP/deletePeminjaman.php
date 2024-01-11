<?php
	include 'koneksi.php';
	$kodePinjam	= $_POST['kodePinjam'];
	
	$query = "DELETE FROM peminjaman WHERE (kodePinjam='".$kodePinjam."')";
	$result = mysqli_query($conn, $query) or die('Error query:  '.$query);
	if ($result == 1){	
		$response["message"]="Success Delete";
		echo json_encode($response);
	}
	else{
		$response["message"]="Failed Delete";
		echo json_encode($response);
	}
	mysqli_close($conn);
?>