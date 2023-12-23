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
        echo "Success";
    } else {
        echo "Failed";
    }
}


?>
