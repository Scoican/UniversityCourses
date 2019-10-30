<?php
session_start();
$database = new PDO('sqlite:C:\xampp\htdocs\lab\examen\examen.sqlite');
$stmt = $database->prepare('SELECT COUNT(*) AS [found] FROM Users WHERE name like :username and pass like :password');
$stmt->bindParam(1, $_POST['user']);
$stmt->bindParam(2, $_POST['pass']);
$stmt->execute();
$row = $stmt->fetch();
$result = intval($row['found']);
if($result===0){
    $_SESSION['user'] = 'no';
}
else{
    $_SESSION['user'] = $_POST['user'];
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>adaugare</title>
    <script src="jq.js"></script>
    <script type="text/javascript" src="jquery.tablesorter.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        span:hover{
            color: orange;
        }
    </style>
    <script>
        
        $(document).ready(function () {

            $('#logout').attr('value','Back');
            $('#logout').click(function () {
                window.location.replace('http://localhost/lab/examen/autentificare.html');
            });

            if('<?php echo $_SESSION['user'];?>' === 'no'){
                $("#show").html('<p>Utilizatorul nu exista!</p>');
            }
            else {
                $.post("joc.html",{
                    user:'<?php echo $_SESSION['user'];?>'
                },function (output) {
                    $('#show').html(output);
                });

            }
        });
    </script>
</head>
<body>
<input type="button" id="logout" value="Logout"><br/>
<div id="show"></div>
<div>

</div>
</body>
</html>
