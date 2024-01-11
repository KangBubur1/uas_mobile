<?php
	include 'koneksi.php';
    $kodeKembali = $_POST['kodeKembali'];
    $tanggalKembali = $_POST['tanggalKembali'];
    $kodePinjam = $_POST['kodePinjam'];
	
	$query = "UPDATE pengembalian SET tanggalKembali='".$tanggalKembali."', kodePinjam='".$kodePinjam."' WHERE kodeKembali='".$kodeKembali."'";
	$result = mysqli_query($conn, $query) or die('Error query:  '.$query);
	if ($result == 1){
		$response['message']="Success Update";
	}
	else{
		$response['message']="Failed Update";
	}
	echo json_encode($response);
	mysqli_close($conn);
?>