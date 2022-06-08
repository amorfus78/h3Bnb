<?php
ini_set("display_error", 1);
include_once '../../models/database.php';
include_once '../../models/contrats.class.php';


//$user->createNewUser();

if($_POST['newcontrats']){

	
	try{
        
        // set values
        
        $contrat->chat=$_POST['chat'];
        $contrat->dates=$_POST['dates'];
        $contrat->idUser=$_POST['idUser'];
        $contrat->idBien=$_POST['idBien'];

        //echo 'Valeur ' . $user->age
        $contrat->createNewContrat();
	
	}catch(Exception $e) {
    //echo 'Exception -> ';
    //var_dump($e->getMessage());
    }   
    
} else{
		 $json['success'] = 0;
		 $json['message'] = 'pas d utilisateur';
         echo json_encode($json);
}

?>