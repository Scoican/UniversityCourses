<?php

$conn = new mysqli("localhost", "root", "root");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sqlCommand = "SELECT COUNT(*) c FROM programare_web.person";
$result = mysqli_query($conn, $sqlCommand);
$size = mysqli_fetch_array($result);
echo $size['c'];