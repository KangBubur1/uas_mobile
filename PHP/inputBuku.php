<?php
include 'koneksi.php';

$kodeBuku = $_POST['kodeBuku'];
$judulBuku = $_POST['judulBuku'];
$pengarang = $_POST['pengarang'];
$penerbit = $_POST['penerbit'];
$tempatTerbit = $_POST['tempatTerbit'];
$jumlahSalinan = $_POST['jumlahSalinan'];
$gambarBuku = $_POST['gambarBuku'];
$kodeKategori = $_POST['kodeKategori'];

// Log values for debugging
error_log("Received data: kodeBuku=$kodeBuku, judulBuku=$judulBuku, pengarang=$pengarang, penerbit=$penerbit, tempatTerbit=$tempatTerbit, jumlahSalinan=$jumlahSalinan, gambarBuku=$gambarBuku, kodeKategori=$kodeKategori");

date_default_timezone_set('Asia/Jakarta');
$path = 'images/' . date("d-m-Y-his") . '-' . rand(100, 10000) . '.jpg';

$query = "INSERT INTO buku (kodeBuku, judulBuku, pengarang, penerbit, tempatTerbit, jumlahSalinan, gambarBuku, kodeKategori) 
VALUES ('".$kodeBuku."','".$judulBuku."','".$pengarang."','".$penerbit."','".$tempatTerbit."','".$jumlahSalinan."','".$path."'
,'".$kodeKategori."')";
$result = mysqli_query($conn, $query) or die('Error query:  '.$query);

if ($result == 1){
    file_put_contents($path, base64_decode($gambarBuku));
    $response["message"]="Success Insert Image";
}
else{
    $response["message"]="Failed To Insert Image";
}

echo json_encode($response);
mysqli_close($conn);
?>