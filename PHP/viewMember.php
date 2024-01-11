<?php
	include 'koneksi.php';
	
	$result = mysqli_query($conn, "SELECT * FROM member where status='member'");
	if (mysqli_num_rows($result) > 0) {
		$items = array();
		while($row = mysqli_fetch_object($result)){
			array_push($items, $row);
		}
		$response['message'] = "Success";
		$response['data'] = $items;
	} 
	else {
		$response['message'] = "Failed";
	}
	 
	echo json_encode($response);
?>