<html>
<body>
<?php

$conn = new mysqli("localhost", "root", "root");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

getPersons($conn);


function getPersons($conn)
{
    $sqlCommand = "SELECT * FROM programare_web.person;";
    $result = mysqli_query($conn, $sqlCommand);
    $index = 1;
    $global_index = $_GET['index'];
    echo "<tr><th>Nume</th><th>Prenume</th><th>E-mail</th></tr>";
    while ($row = mysqli_fetch_array($result)) {
        if($global_index + 3 <=$index){
            break;
        }
        if($index >= $global_index) {
            echo "<tr id=" . $index . ">" . "<td>" . $row['name'] . "</td>";
            echo "<td>" . $row['surname'] . "</td>";
            echo "<td>" . $row['email'] . "</td>" . "</tr>";
        }
        $index++;
    }
}

mysqli_close($conn);
?>
</body>
</html>