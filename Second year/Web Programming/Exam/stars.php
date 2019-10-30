<?php
$database = new PDO('sqlite:C:\xampp\htdocs\lab\examen\examen.sqlite');

$user = $_POST['user'];
$stmt = $database->prepare('SELECT name, poza FROM Users');
$stmt->execute();

$result = '<ul>';
//while($row = $stmt->fetch()){
    //if($row['name'] !== $user) {
        /*$result = $result . '<li><img src="' . $row['poza'] . '" style="width:200px">';
        $result = $result . '<span class="fa fa-star" id="1|' . $row['name'] . '" onclick="star(this)"></span>';
        $result = $result . '<span class="fa fa-star" id="2|' . $row['name'] . '" onclick="star(this)"></span>';
        $result = $result . '<span class="fa fa-star" id="3|' . $row['name'] . '" onclick="star(this)"></span>';
        $result = $result . '<span class="fa fa-star" id="4|' . $row['name'] . '" onclick="star(this)"></span>';
        $result = $result . '<span class="fa fa-star" id="5|' . $row['name'] . '" onclick="star(this)"></span>';
        $result = $result . '</li>';*/
    //}
//}
$result = $result . '</ul>';
echo $result;