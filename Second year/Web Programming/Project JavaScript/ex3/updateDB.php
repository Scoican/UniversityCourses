<?php

$conn = new mysqli("localhost", "root", "root");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sqlCommand = "UPDATE programare_web.person
SET name = '".$_POST['name']."', surname = '".$_POST['surname']."', email = '".$_POST['email']."'
WHERE id =".$_POST['id'].";";

if($conn->query($sqlCommand) == TRUE)
    echo "Salvat";
else
    echo "Nu s-a putut salva";