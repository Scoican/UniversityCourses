<html>
<body>
<?php
$conn = new mysqli("localhost", "root", "root");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


getDepartures($conn);

function getDepartures($conn){
    $sqlCommand = "SELECT DISTINCT departure FROM departure";
    $result = mysqli_query($conn,$sqlCommand);
    while($row = mysqli_fetch_array($result)) {
        echo "<li id ="."depart".">" . $row['departure'] . "</li>";
    }
}
mysqli_close($conn);
?>
</body>
</html>