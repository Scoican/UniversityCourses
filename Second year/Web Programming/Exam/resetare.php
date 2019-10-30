<?php
$database = new PDO('sqlite:C:\xampp\htdocs\lab\examen\examen.sqlite');
$stmt = $database->prepare('UPDATE Users SET pass=:p where name=:n and data=:data');
$stmt->bindParam(1, $_POST['pass']);
$stmt->bindParam(2, $_POST['user']);
$stmt->bindParam(3, $_POST['date']);
$stmt->execute();
echo "OK";