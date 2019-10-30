<?php
$database = new PDO('sqlite:C:\xampp\htdocs\lab\examen\examen.sqlite');

$stmt = $database->prepare('SELECT name, poza FROM Users');
$stmt->execute();

$result = '<ul>';
while($row = $stmt->fetch()){
    $result = $result . '<li><label>' . $row['name'] . '</label><img src="' . $row['poza'] . '" style="width:200px"></li>';
}
$result = $result . '</ul>';
echo $result;