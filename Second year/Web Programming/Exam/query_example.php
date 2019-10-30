<?php
//login
$database = new PDO('sqlite:C:\xampp\htdocs\lab\examen\examen.sqlite');
$stmt = $database->prepare('SELECT COUNT(*) AS [found] FROM Users WHERE username like :username and password like :password');
$stmt->bindParam(1, $_POST['username']);
$stmt->bindParam(2, $_POST['password']);
$stmt->execute();
$row = $stmt->fetch();
$result = intval($row['found']);
if($result===0){
    echo 'gresit';
}
else{
    echo 'corect';
}

//get json
$stmt = $database->prepare('SELECT username FROM activi');
$stmt->execute();
echo json_encode($stmt->fetchAll(PDO::FETCH_ASSOC));