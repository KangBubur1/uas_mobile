<?php
include 'koneksi.php';

if(isset($_POST['kodeBuku'])) {
    $kodeBuku = $_POST['kodeBuku'];

    // Fetch data before deletion
    $data = mysqli_query($conn, "SELECT * FROM buku WHERE kodeBuku='$kodeBuku'");
    $row = mysqli_fetch_array($data);

    if ($row) {
        // Delete image file
        unlink($row['gambarBuku']);

        // Delete data from the database
        $query = "DELETE FROM buku WHERE kodeBuku='$kodeBuku'";
        $result = mysqli_query($conn, $query);

        if ($result) {
            $response["message"] = "Success Delete";
            echo json_encode($response);
        } else {
            $response["message"] = "Failed Delete: " . mysqli_error($conn);
            echo json_encode($response);
        }
    } else {
        $response["message"] = "Record not found";
        echo json_encode($response);
    }
} else {
    $response["message"] = "Missing parameter: kodeBuku";
    echo json_encode($response);
}

mysqli_close($conn);
?>
