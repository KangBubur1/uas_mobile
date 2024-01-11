<?php
include 'koneksi.php';

$kodeBuku = $_POST['kodeBuku'];
$judulBuku = $_POST['judulBuku'];
$pengarang = $_POST['pengarang'];
$penerbit = $_POST['penerbit'];
$tempatTerbit = $_POST['tempatTerbit'];
$jumlahSalinan = $_POST['jumlahSalinan'];
$kodeKategori = $_POST['kodeKategori'];

$query = "UPDATE buku SET kodeBuku='$kodeBuku', judulBuku='$judulBuku', pengarang='$pengarang', penerbit='$penerbit',
    tempatTerbit='$tempatTerbit', jumlahSalinan='$jumlahSalinan', kodeKategori='$kodeKategori' WHERE kodeBuku='$kodeBuku'";

$result = mysqli_query($conn, $query) or die('Error query: ' . $query);

if ($result == 1) {
    file_put_contents($path, base64_decode($gambarBuku));
    $response['message'] = "Success Update";
} else {
    $response['message'] = "Failed Update";
}

echo json_encode($response);

mysqli_close($conn);
?>
