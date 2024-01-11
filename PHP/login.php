<?php
include_once "koneksi.php"; 
include_once "validate.php";

// Check if username and password are set
if (isset($_POST['username']) && isset($_POST['password'])) {
    // Validate input
    $username = validate($_POST['username']);
    $password = validate($_POST['password']);

    // Perform database query
    $sql = "SELECT * FROM member WHERE username='$username' AND password='" . md5($password) . "'";
    $result = $conn->query($sql);

    // Check the result
    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        $userStatus = $row['status']; 
        $name = $row['name'];
        $password = $row['password'];
        $tempatLahir = $row['tempatLahir'];
        $tanggalLahir = $row['tanggalLahir'];
        $noTelepon = $row['noTelepon'];
        $idMember = $row['idMember'];
        echo "Success|$userStatus|$name|$password|$tempatLahir|$tanggalLahir|$noTelepon|$idMember";
    } else {
        echo "Failed";
    }
}


?>
