<?php
$database = new PDO('sqlite:C:\xampp\htdocs\lab\examen\examen.sqlite');
function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}

$user = test_input($_POST['user']);
$pass = test_input($_POST['pass']);
$desc = test_input($_POST['desc']);
$date = test_input($_POST['date']);
$filename = $_FILES['file']['name'];
$location = "images/".$filename;
$imageFileType = pathinfo($location,PATHINFO_EXTENSION);
$valid_extensions = array("jpg","jpeg","png");

if(in_array(strtolower($imageFileType),$valid_extensions)){
    if(move_uploaded_file($_FILES['file']['tmp_name'],$location)){
        $stmt = $database->prepare('UPDATE Users SET data=:data, descriere=:desc, poza=:po where name=:n and pass=:p');
        $stmt->bindParam(1, $date);
        $stmt->bindParam(2, $desc);
        $stmt->bindParam(3, $location);
        $stmt->bindParam(4, $user);
        $stmt->bindParam(5, $pass);
        $stmt->execute();
        echo 'OK';
    }
    else
        echo 'Nope!';
}
else
    echo 'Invalid format!';