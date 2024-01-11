<?php
include('koneksi.php');

$query = "SELECT buku.kodeBuku,buku.judulBuku, buku.pengarang, kategori.namaKategori, buku.gambarBuku
          FROM buku
          JOIN kategori ON buku.kodeKategori = kategori.kodeKategori";

// Use prepared statement
$stmt = mysqli_prepare($conn, $query);

if (!$stmt) {
    // Query preparation failed, handle the error
    die('Error in SQL query preparation: ' . mysqli_error($conn) . "\nQuery: " . $query);
}

// Execute statement
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
        'pengarang' => $row['pengarang'],
        'kategori' => $row['namaKategori'],
        'gambarBuku' => $row['gambarBuku']
    );

    $books[] = $book;
}

// Encode the array of books to JSON
echo json_encode($books);

// Close the database connection
mysqli_close($conn);
?>
