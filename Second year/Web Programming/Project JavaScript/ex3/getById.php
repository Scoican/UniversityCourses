<?php

$conn = new mysqli("localhost", "root", "root");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
getIds($conn);
function getIds($conn)
{
    $sqlCommand = "SELECT * FROM programare_web.person WHERE id = " . $_GET['id'];
    $result = mysqli_query($conn, $sqlCommand);
    while ($row = mysqli_fetch_array($result)) {
        echo $row['name'] . "," . $row['surname'] . "," . $row['email'];
    }
}
mysqli_close($conn);