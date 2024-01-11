<?php
session_start();
include "koneksi.php";

$response = array();

if (isset($_SESSION['username'])) {
    $username = $_SESSION['username'];
    
    // Query untuk mendapatkan data pengguna berdasarkan username dari sesi
    $query = "SELECT * FROM member WHERE username = ?";
    $stmt = $conn->prepare($query);
    $stmt->bind_param('s', $username);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        $response['status'] = 'success';
        $response['user'] = $row;  // Mengembalikan data pengguna dalam bentuk array
    } else {
        $response['status'] = 'failed';
        $response['message'] = 'User not found';
    }
    $stmt->close();
} else {
    $response['status'] = 'failed';
    $response['message'] = 'User not logged in';
}

echo json_encode($response);
?>
