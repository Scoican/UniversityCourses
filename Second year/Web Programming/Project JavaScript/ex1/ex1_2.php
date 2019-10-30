<html>
<body>
<?php

$conn = new mysqli("localhost", "root", "root");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
getArrivals($conn,$_GET['q']);
function getArrivals($conn,$departureCity){
    $sqlCommand = "SELECT DISTINCT arrival FROM programare_web.circulation WHERE departure = '" . $departureCity . "' ;";
    $result = mysqli_query($conn,$sqlCommand);
    while($row = mysqli_fetch_array($result)) {
        echo "<li class ="."arrivals".">" . $row['arrival'] . "</li>";
    }
}
mysqli_close($conn);
?>
</body>
</html>