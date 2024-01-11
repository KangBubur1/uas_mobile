<?php
include 'koneksi.php';

// Periksa apakah data POST diterima
if (isset($_POST['idMember'], $_POST['username'], $_POST['name'], $_POST['tempatLahir'], $_POST['tanggalLahir'], $_POST['noTelepon'])) {

    $idMember = $_POST['idMember'];
    $username = $_POST['username'];
    $name = $_POST['name'];
    $tempatLahir = $_POST['tempatLahir'];
    $tanggalLahir = $_POST['tanggalLahir'];
    $noTelepon = $_POST['noTelepon'];

    // Kueri untuk memperbarui data member
    $query = "UPDATE member SET username='$username', name='$name', tempatLahir='$tempatLahir', tanggalLahir='$tanggalLahir', noTelepon='$noTelepon' WHERE idMember='$idMember'";
    
    // Eksekusi kueri
    $result = mysqli_query($conn, $query);

    // Membuat respons berdasarkan hasil kueri
    if ($result) {
        $response['message'] = "Success Update";
    } else {
        $response['message'] = "Failed Update: " . mysqli_error($conn); // Menambahkan informasi error ke respons
    }
    
    // Mengembalikan respons dalam format JSON
    echo json_encode($response);
    
    // Menutup koneksi database
    mysqli_close($conn);
} else {
    // Jika data POST tidak diterima, kirimkan pesan error
    $response['message'] = "Invalid POST data";
    echo json_encode($response);
}
?>
