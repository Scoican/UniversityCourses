<html>
<body>
<?php

$conn = new mysqli("localhost", "root", "root");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
getNotebooks($conn);
function getNotebooks($conn){
    $producator = $_POST['producator'];
    $procesor = $_POST['procesor'];
    $placavideo = $_POST['placavideo'];
    $memorie = $_POST['memorie'];
    $sqlCommand = "SELECT * FROM programare_web.notebook;";
    $result = mysqli_query($conn,$sqlCommand);
    $notebooks = [];
    while($row = mysqli_fetch_array($result)) {
        $notebook = array($row['producator'],$row['procesor'],$row['placavideo'],$row['memorie'],$row['name']);
        array_push($notebooks,$notebook);
    }
    if($producator != "-"){
        $notebooks = filterByProducator($producator,$notebooks);
        echo "-\n";

    }
    if($procesor != "-" && !empty($notebooks)){
        $notebooks = filterByProcesor($procesor,$notebooks);
        echo "-\n";

    }
    if($placavideo != "-" && !empty($notebooks)){
        $notebooks = filterByPlacaVideo($placavideo,$notebooks);
        echo "-\n";

    }
    if($memorie != "-" && !empty($notebooks)){
        $notebooks = filterByMemorie($memorie,$notebooks);
        echo "-\n";

    }
    if(!empty($notebooks)) {
        foreach ($notebooks as $notebook) {
            echo "<li>".$notebook[4]."</li>";
        }
    }else{
        echo "<li>Nu exista nici un produs conform filtrelor efectuate!</li>";
    }
}

function filterByProducator($producator,$list){
    $result = [];
    foreach ($list as $notebook){
        if($notebook[0] == $producator){
            array_push($result,$notebook);
        }
    }
    return $result;
}


function filterByProcesor($procesor,$list){
    $result = [];
    foreach ($list as $notebook){
        if($notebook[1] == $procesor){
            array_push($result,$notebook);
        }
    }
    return $result;
}


function filterByPlacaVideo($placavideo,$list){
    $result = [];
    foreach ($list as $notebook){
        if($notebook[2] == $placavideo){
            array_push($result,$notebook);
        }
    }
    return $result;
}


function filterByMemorie($memorie,$list){
    $result = [];
    foreach ($list as $notebook){
        if($notebook[3] == $memorie){
            array_push($result,$notebook);
        }
    }
    return $result;
}
mysqli_close($conn);
?>
</body>
</html>