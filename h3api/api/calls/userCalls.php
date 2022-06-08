<?php
ini_set("display_error", 1);
include_once '../../models/database.php';
include_once '../../models/user.class.php';


//$user->createNewUser();

if($_POST['newuser']){

	
	try{
        

        // set values
        $user->fname=$_POST['fname'];
        $user->lnamme=$_POST['lname'];
        $user->email=$_POST['email'];
        $user->password=$_POST['password'];
        $user->phone=$_POST['phone'];

        //echo 'Valeur ' . $user->age
        $user->createNewUser();
	
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