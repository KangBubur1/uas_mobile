<?php
include 'koneksi.php';

$kodeBuku = $_POST['kodeBuku'];

$sql = "SELECT jumlahSalinan FROM buku WHERE kodeBuku = '$kodeBuku'";

$stmt = mysqli_prepare($conn,$sql);
mysqli_stmt_execute($stmt);

// Bind the result variable
mysqli_stmt_bind_result($stmt, $jumlahSalinan);

// Fetch the result
mysqli_stmt_fetch($stmt);


if ($jumlahSalinan > 1) {
    echo json_encode(['status' => 'success']);
} else {
    echo json_encode(['status' => 'failure']);
}

// Close the statement and connection
mysqli_stmt_close($stmt);
mysqli_close($conn);

?>