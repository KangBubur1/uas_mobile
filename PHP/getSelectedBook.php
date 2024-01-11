<?php

include 'koneksi.php';

$idMember = $_GET['idMember'];


$query = "SELECT buku.gambarBuku
          FROM buku
          JOIN history
          ON buku.kodeBuku = history.kodeBuku
          WHERE history.idMember = '$idMember'
          GROUP BY buku.gambarBuku";
$result = mysqli_query($conn, $query);


    if (!$result) {
        // Query failed, handle the error
        die('Error in SQL query: ' . mysqli_error($conn) . "\nQuery: " . $query);
    }

    $books = array();

    while ($row = mysqli_fetch_assoc($result)) {
        // Append each book to the array
        $book = array(
            'gambarBuku' => $row['gambarBuku'],
        );

        $books[] = $book;
    }

    // Encode the array of books to JSON
    echo json_encode($books);

    // Close the database connection
    mysqli_close($conn);
?>