<?php

$dir = $_POST["dir"];
$id = intval($_POST["id"]);
writeOneDirectory($id,$dir);
function writeOneDirectory($id,$dir)
{
    if(is_dir($dir)) {
        $files = scandir($dir);
    }else{
        $lines = file($dir);
        foreach($lines as $line)
        {
            echo " &id=text&".$line;
        }
        return;
    }
    echo "<ul>";
    foreach ($files as $file) {
        if($file != "." && $file != "..") {
            $id++;
            echo "<li id=\"". $id ."\" class=\"".$dir."\" onclick=\"showDirectories(this)\">";
            echo $file;
            echo "</li>";
        }
    }
    echo "</ul>";
    echo "&id=".$id;
}
?>