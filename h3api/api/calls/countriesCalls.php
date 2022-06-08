<?php
ini_set("display_error", 1);
include_once '../../models/database.php';
include_once '../../models/countries.class.php';


$tab = [["Netherlands", "NED"],["Spain", "SPA"],["France", "FRA"]];


for ($i = 0; $i<sizeof($tab); $i++){
    $countries = new Countries($db);

    $countries->name = $tab[$i][0];
    $countries->shortname = $tab[$i][1];

    $countries->createNewCountry();

}

?>
