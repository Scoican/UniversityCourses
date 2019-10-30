<?php
$database = new PDO('sqlite:C:\xampp\htdocs\lab\examen\examen.sqlite');
$stmt = $database->prepare('select V.name, V.medie, U.descriere, U.poza, U.data
from Voturi V
inner join Users U on U.name=V.name
order by V.medie DESC
LIMIT 5');
$stmt->execute();
echo json_encode($stmt->fetchAll(PDO::FETCH_ASSOC));