<?php
ini_set("display_error", 1);
include_once '../../models/database.php';
include_once '../../models/biens.class.php';

$string = file_get_contents('../../assets/test.json');

$jsonIterator = new RecursiveIteratorIterator(
    new RecursiveArrayIterator(json_decode($string, TRUE)),
    RecursiveIteratorIterator::SELF_FIRST);

    $file = file_get_contents("../../assets/appart1.txt", FILE_USE_INCLUDE_PATH);



foreach ($jsonIterator as $key => $val) {
    


    if(is_array($val)) {
        $fields =  $val['fields'];
        $geolocation = $fields['geolocation'];
        $latitude = $geolocation[0];
        $longitude = $geolocation[1];
        $type = $fields['property_type'];
        $description = $fields['description'];
        $beds = $fields['beds'];
        $city = $fields['city'];
        $price = $fields['price'];
        $idUser = 5;

        //echo $city;

        if ($city == "Paris" || $city == "Barcelona"){
            if ($city == "Paris" ){$idCity = 4;}
            if ($city == "Barcelona" ){$idCity = 1;}
            echo $city;

            $bien = new Bien($db);

            $bien->beds = $beds;
            $bien->latitude = $latitude;
            $bien->longitude = $longitude;
            $bien->type = $type;
            $bien->description = $description;
            $bien->availabilities = "ALL";
            $bien->idCity = $idCity;
            $bien->idOwner = $idUser;
            $bien->price = $price;
            $bien->image = $file;

            $bien->createNewBien();
        }

    } 
}
