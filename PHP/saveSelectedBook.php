<?php

include 'koneksi.php';

$kodeBuku = $_POST['kodeBuku'];
$idMember = $_POST['idMember'];


$query = "INSERT INTO history  VALUES ('$idMember','$kodeBuku')";
$result = mysqli_query($conn, $query);

if ($result) {
    // Return a success response if needed
    echo json_encode(['success' => true]);
} else {
    // Return an error response if needed
    echo json_encode(['success' => false, 'error' => mysqli_error($conn)]);
}
error_log(print_r($_POST, true));
?>