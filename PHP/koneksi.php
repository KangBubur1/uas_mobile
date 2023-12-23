<?php
$host = "localhost";
$user = "root";
$password = "";
$database = "perpustakaan_mobile";
$conn = mysqli_connect($host, $user, $password, $database);

if (!$conn) {
    echo "ERR_CONNECTION_DATABASE";
    exit; // Terminate script if connection fails
}
?>
