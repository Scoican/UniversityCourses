<html>
<body>
<?php

$conn = new mysqli("localhost", "root", "root");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
getIds($conn);
function getIds($conn){
    $sqlCommand = "SELECT id FROM programare_web.person";
    $result = mysqli_query($conn,$sqlCommand);
    while($row = mysqli_fetch_array($result)) {
        echo "<option>" . $row['id'] . "</option>";
    }
}
mysqli_close($conn);
?>
</body>
</html>