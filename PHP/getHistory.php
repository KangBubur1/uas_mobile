<?php
include('koneksi.php');

// Check if the 'idMember' parameter is present in the GET request
if (isset($_GET['idMember'])) {
    // Get the 'idMember' value from the GET request
    $idMember = $_GET['idMember'];

    $query = "SELECT buku.kodeBuku, buku.judulBuku, buku.gambarBuku, peminjaman.tanggalPinjam, peminjaman.tanggalPengembalian, peminjaman.statusPeminjaman
             FROM buku
             JOIN peminjaman 
             ON buku.kodeBuku = peminjaman.kodeBuku
             WHERE peminjaman.idMember = '$idMember'";

    $stmt = mysqli_prepare($conn, $query);
    if (!$stmt) {
        // Query preparation failed, handle the error
        die('Error in SQL query preparation: ' . mysqli_error($conn) . "\nQuery: " . $query);
    }

    $executeResult = mysqli_stmt_execute($stmt);

    if (!$executeResult) {
        // Execution failed, handle the error
        die('Error in executing statement: ' . mysqli_error($conn));
    }

    // Get result
    $result = mysqli_stmt_get_result($stmt);

    if (!$result) {
        // Query failed, handle the error
        die('Error in SQL query: ' . mysqli_error($conn) . "\nQuery: " . $query);
    }

    $books = array();

    while ($row = mysqli_fetch_assoc($result)) {
        // Append each book to the array
        $book = array(
            'kodeBuku' => $row['kodeBuku'],
            'judulBuku' => $row['judulBuku'],
            'gambarBuku' => $row['gambarBuku'],
            'tanggalPinjam' => $row['tanggalPinjam'],
            'tanggalPengembalian' => $row['tanggalPengembalian'],
            'statusPeminjaman' => $row['statusPeminjaman']
        );

        $books[] = $book;
    }

    // Encode the array of books to JSON
    echo json_encode($books);

    // Close the database connection
    mysqli_close($conn);
} else {
    // 'idMember' parameter is not present in the request
    echo json_encode(array('error' => 'Missing idMember parameter'));
}
?>
