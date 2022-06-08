<?php
ini_set("display_error", 1);
include_once '../../models/database.php';
include_once '../../models/cities.class.php';


$tab = [["Barcelona","41.3850639", "2.1734035", 2],["Amsterdam", "52.370216", "4.895168",1],["Paris", "48.856614", "2.3522219",3]];


for ($i = 0; $i<sizeof($tab); $i++){
    $cities = new Cities($db);

    $cities->name = $tab[$i][0];
    $cities->latitude = $tab[$i][1];
    $cities->longitude = $tab[$i][2];
    $cities->idPays = $tab[$i][3];

    $cities->createNewCountry();

}

?>
