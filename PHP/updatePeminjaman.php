<?php
	include 'koneksi.php';
    $kodePinjam = $_POST['kodePinjam'];
    $tanggalPinjam = $_POST['tanggalPinjam'];
    $tanggalPengembalian = $_POST['tanggalPengembalian'];
    $kodeBuku = $_POST['kodeBuku'];
    $idMember = $_POST['idMember'];
	
	$query = "UPDATE peminjaman SET tanggalPinjam='".$tanggalPinjam."',tanggalPengembalian='".$tanggalPengembalian."',kodeBuku='".$kodeBuku."',idMember='".$idMember."' WHERE kodePinjam='".$kodePinjam."'";
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