<?php
$database = new PDO('sqlite:C:\xampp\htdocs\lab\examen\examen.sqlite');
if($_POST['pass1'] === $_POST['pass2']){
    $stmt = $database->prepare('INSERT INTO Users (name, pass) VALUES (:n,:p)');
    $stmt->bindParam(1, $_POST['user']);
    $stmt->bindParam(2, $_POST['pass1']);
    $stmt->execute();
    $stmt = $database->prepare('INSERT INTO Voturi (name,stars,number) VALUES (:n,:st,:nr)');
    $stmt->bindParam(1, $_POST['user']);
    $stmt->bindValue(2, 0);
    $stmt->bindValue(3, 0);
    $stmt->execute();
}