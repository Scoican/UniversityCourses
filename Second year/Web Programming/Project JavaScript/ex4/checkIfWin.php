<?php

checkIfWin();
function checkIfWin(){
    $table = [];
    $serialized = $_POST['serializabled'];
    $table = deserializeTable($table,$serialized);
    $sum = 0;
    for($i=0;$i<3;$i++) {
        if($table[$i][$i] == -1){
            $sum = -1;
            break;
        }
        $sum += $table[$i][$i];
    }
    if(winnerFound($sum)){
        return;
    }
    $sum = 0;
    for($i=2;$i>=0;$i--) {
        if($table[$i][2-$i] == -1){
            $sum = -1;
            break;
        }
        $sum += $table[$i][2-$i];
    }
    if(winnerFound($sum)){
        return;
    }
    for($i=0;$i<3;$i++){
        $sum = 0;
        for($j=0;$j<3;$j++){
            if($table[$i][$j] == -1){
                $sum = -1;
                break;
            }
            $sum += $table[$i][$j];
        }
        if(winnerFound($sum)){
            return;
        }
        $sum = 0;
        for($j=0;$j<3;$j++){
            if($table[$j][$i] == -1){
                $sum = -1;
                break;
            }
            $sum += $table[$j][$i];
        }
        if(winnerFound($sum)){
            return;
        }
    }
    $game_over = true;
    for($i=0;$i<3;$i++){
        for($j=0;$j<3;$j++){
            if($table[$i][$j] == -1){
                $game_over = false;
            }
        }
    }
    if($game_over == true){
        echo 'G';
    }
    else{
        echo '-';
    }
}

function winnerFound($sum){
    if($sum == 0){
        echo '0';
        return true;
    }
    if($sum == 3){
        echo 'X';
        return true;
    }
    return false;
}

function deserializeTable($table,$serializable) {
    $serializableList = explode(",",$serializable);
    $index = 0;
    for($i=0;$i<3;$i++){
        for($j=0;$j<3;$j++){
            $value = intval($serializableList[$index]);
            $table[$i][$j] = $value;
            $index++;
        }
    }

    return $table;
}