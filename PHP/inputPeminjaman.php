<?php
include 'koneksi.php';

$kodePinjam = $_POST['kodePinjam'];
$tanggalPinjam = $_POST['tanggalPinjam'];
$tanggalPengembalian = $_POST['tanggalPengembalian'];
$kodeBuku = $_POST['kodeBuku'];
$idMember = $_POST['idMember'];


$query = "INSERT INTO peminjaman (kodePinjam, tanggalPinjam, tanggalPengembalian, kodeBuku, idMember) 
VALUES ('".$kodePinjam."','".$tanggalPinjam."','".$tanggalPengembalian."','".$kodeBuku."','".$idMember."')";
$result = mysqli_query($conn, $query) or die('Error query:  '.$query);

if ($result == 1){
    $response["message"]="Success";
}
else{
    $response["message"]="Failed";
}

echo json_encode($response);
mysqli_close($conn);
?>