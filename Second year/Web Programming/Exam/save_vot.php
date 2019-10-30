<?php
$database = new PDO('sqlite:C:\xampp\htdocs\lab\examen\examen.sqlite');
$vot = $_POST['vote'];
$data = explode('|',$vot);
$stars_add = intval($data[0]);
//get old values
$stmt = $database->prepare('SELECT stars,number FROM Voturi WHERE name=:n');
$stmt->bindParam(1, $data[1]);
$stmt->execute();
$row = $stmt->fetch();
$stars_old = intval($row['stars']);
$number_old = intval($row['number']);
$stars_new = $stars_old + $stars_add;
$number_new = $number_old + 1;
$media_new = $stars_new/$number_new;
//set new values
$stmt = $database->prepare('UPDATE Voturi SET stars=:s, number=:numb, medie=:med WHERE name=:n');
$stmt->bindParam(1, $stars_new);
$stmt->bindParam(2, $number_new);
$stmt->bindParam(3, $media_new);
$stmt->bindParam(4, $data[1]);
$stmt->execute();
echo $data[0] . '|' . $data[1] . '|' . $stars_old . '|' . $stars_new . '|' . $number_old . '|' . $number_new . '|' . $media_new;