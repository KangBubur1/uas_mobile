<?php
include 'koneksi.php';

// Check if the required POST parameters are set
if (isset($_POST['kodeBuku'], $_POST['judulBuku'], $_POST['pengarang'], $_POST['penerbit'], $_POST['tempatTerbit'], $_POST['jumlahSalinan'])) {
    $kodeBuku = $_POST['kodeBuku'];
    $judulBuku = $_POST['judulBuku'];
    $pengarang = $_POST['pengarang'];
    $penerbit = $_POST['penerbit'];
    $tempatTerbit = $_POST['tempatTerbit'];
    $jumlahSalinan = $_POST['jumlahSalinan'];

    // SQL query to insert data into the database
    $sql = "INSERT INTO buku (kodeBuku, judulBuku, pengarang, penerbit, tempatTerbit, jumlahSalinan) VALUES ('$kodeBuku', '$judulBuku', '$pengarang', '$penerbit', '$tempatTerbit', '$jumlahSalinan')";

    if ($conn->query($sql) === TRUE) {
        echo "Data inserted successfully";
    } else {
        echo "Error: " . $sql . "<br>" . $conn->error;
    }
} 


?>
