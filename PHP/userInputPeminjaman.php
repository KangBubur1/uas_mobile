<?php
include 'koneksi.php';

$kodePinjam = $_POST['kodePinjam'];  
$tanggalPinjam = $_POST['tanggalPinjam'];  
$tanggalPengembalian = $_POST['tanggalPengembalian'];  
$kodeBuku = $_POST['kodeBuku'];  
$idMember = $_POST['idMember'];  

$query = "INSERT INTO peminjaman 
          VALUES ('".$kodePinjam."','".$tanggalPinjam."','".$tanggalPengembalian."','".$kodeBuku."','".$idMember."','pending')";
$result = mysqli_query($conn, $query) or die('Error query:  '.$query);

if ($result){
    echo json_encode(['status' => 'success']);
} else {
    echo json_encode(['status' => 'failure']);
}

mysqli_close($conn);
?>
