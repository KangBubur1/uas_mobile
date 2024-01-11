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

date_default_timezone_set('Asia/Jakarta');
$path = 'images/' . date("d-m-Y-his") . '-' . rand(100, 10000) . '.jpg';

$data = mysqli_query($conn, "SELECT * FROM buku WHERE kodeBuku='$kodeBuku'");
$d = mysqli_fetch_array($data);

if (file_exists($d['gambarBuku'])) {
    unlink($d['gambarBuku']);
} else {
    echo "File to delete not found.";
}

$query = "UPDATE buku SET kodeBuku='$kodeBuku', judulBuku='$judulBuku', pengarang='$pengarang', penerbit='$penerbit',
    tempatTerbit='$tempatTerbit', jumlahSalinan='$jumlahSalinan', gambarBuku='$path', kodeKategori='$kodeKategori' WHERE kodeBuku='$kodeBuku'";

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
