<?php
include 'koneksi.php';

$kodeBuku = $_POST['kodeBuku'];

// Update the book stock
$sqlUpdate = "UPDATE buku SET jumlahSalinan = jumlahSalinan - 1 WHERE kodeBuku = ? AND jumlahSalinan > 0";
$stmtUpdate = mysqli_prepare($conn, $sqlUpdate);

mysqli_stmt_bind_param($stmtUpdate, "s", $kodeBuku);

// Execute the update query
if (mysqli_stmt_execute($stmtUpdate)) {
    // Check if any rows were affected by the update
    $rowsAffected = mysqli_stmt_affected_rows($stmtUpdate);

    if ($rowsAffected > 0) {
        // Book stock updated successfully
        echo json_encode(['status' => 'success']);
    } else {
        // No rows were updated, meaning the book stock is already at its minimum
        echo json_encode(['status' => 'failure', 'message' => 'Book stock is already at its minimum']);
    }
} else {
    // Book stock update failed
    echo json_encode(['status' => 'failure']);
}

// Close the statement
mysqli_stmt_close($stmtUpdate);
mysqli_close($conn);
?>
