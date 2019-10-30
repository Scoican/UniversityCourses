<?php

move();
function getAvailablePositions($serializedList)
{
    $positionList[] = [];
    for ($i = 0; $i < 9; $i++) {
        if ($serializedList[$i] == '-1') {
            array_push($positionList, $i);
        }
    }
    return $positionList;
}

function move()
{
    $serialized = $_POST['serializabled'];
    $serializedList = explode(",", $serialized);
    $computerSymbol = $serializedList[count($serializedList) - 1];
    $positionList = getAvailablePositions($serializedList);
    $position = $positionList[rand(1, count($positionList) - 1)];
    if ($computerSymbol == 'X') {
        $serializedList[$position] = '1';
    } else {
        $serializedList[$position] = '0';
    }
    for ($i = 0; $i < 9; $i++) {
        if ($i == 8) {
            echo $serializedList[$i];
            break;
        }
        echo $serializedList[$i] . ",";
    }
}