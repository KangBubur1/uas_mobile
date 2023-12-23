<?php
// Check if all required POST parameters are set
if (
    isset($_POST['username']) &&
    isset($_POST['name']) &&
    isset($_POST['password']) &&
    isset($_POST['tempatLahir']) &&
    isset($_POST['tanggalLahir']) &&
    isset($_POST['noTelepon']) &&
    isset($_POST['tanggalRegistrasi'])
) {
    // Include necessary files
    require_once 'koneksi.php';
    require_once 'validate.php';

    // Validate and sanitize input data
    $username = validate($_POST['username']);
    $name = validate($_POST['name']);
    $password = validate($_POST['password']);
    $tempatLahir = validate($_POST['tempatLahir']);
    $tanggalLahir = validate($_POST['tanggalLahir']);
    $noTelepon = validate($_POST['noTelepon']);
    $tanggalRegistrasi = validate($_POST['tanggalRegistrasi']);

    $hashedPassword = md5($password);

    $stmt = $conn->prepare("INSERT INTO member 
                           VALUES('', ?, ?, ?, ?, ?, ?, ?)");

    $stmt->bind_param(
        "sssssss",
        $username,
        $name,
        $hashedPassword,
        $tempatLahir,
        $tanggalLahir,
        $noTelepon,
        $tanggalRegistrasi
    );


    if ($stmt->execute()) {
        echo "Success";
    } else {
        echo "Failure";
    }


    $stmt->close();

} else {
    echo "One or more required fields are missing.";
}
?>
