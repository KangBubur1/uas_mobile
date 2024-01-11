<?php
include 'koneksi.php';

$kodeKembali = $_POST['kodeKembali'];
$tanggalKembali = $_POST['tanggalKembali'];
$kodePinjam = $_POST['kodePinjam'];

// Insert data into pengembalian table
$queryInsert = "INSERT INTO pengembalian (kodeKembali, tanggalKembali, kodePinjam) 
                VALUES ('".$kodeKembali."','".$tanggalKembali."','".$kodePinjam."')";
$resultInsert = mysqli_query($conn, $queryInsert) or die('Error query:  '.$queryInsert);

// Update status in peminjaman table
$queryUpdate = "UPDATE peminjaman SET tanggalPengembalian = '$tanggalKembali', statusPeminjaman = 'returned' WHERE kodePinjam = '".$kodePinjam."'";
$resultUpdate = mysqli_query($conn, $queryUpdate) or die('Error query:  '.$queryUpdate);

$response = array();

if ($resultInsert == 1 && $resultUpdate == 1){
    $response["message"]="Success";
} else {
    $response["message"]="Failed";
}

echo json_encode($response);
mysqli_close($conn);
?>