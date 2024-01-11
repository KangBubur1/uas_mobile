<?php
	include 'koneksi.php';
	$kodeKembali	= $_POST['kodeKembali'];
	
	$query = "DELETE FROM pengembalian WHERE (kodeKembali='".$kodeKembali."')";
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