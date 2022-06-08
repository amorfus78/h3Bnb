<?php

use function PHPSTORM_META\type;

    include_once 'database.php';

	class User{
     
        // database connection and table name
        private $conn;
        private $table_name = "user";

        // object properties
        public $id;
        public $fname;
        public $lname;
        public $email;
        public $password;
        public $phone;


        public function __construct($db){
            $this->conn = $db;
        }
        
        public function createNewUser(){
            
            $query = "INSERT INTO user(fname, lname, email, password, phone) VALUES(?,?,?,?,?)";

            // posted values
            $this->fname=addslashes(strip_tags($this->fname));
            $this->lname=htmlspecialchars(strip_tags($this->lname));
            $this->email=htmlspecialchars(strip_tags($this->email));
            $this->password=htmlspecialchars(strip_tags($this->password));
            $this->phone=htmlspecialchars(strip_tags($this->phone));                
           
            // prepare query
            $stmt = $this->conn->prepare($query);

            if($stmt->execute(array($this->fname,$this->lname,$this->email,$this->password,$this->phone))){
                $json['success'] = 1;
                $json['message'] = 'insert successfull '.$stmt->queryString;
                echo json_encode($json);
            }else{
                $json['success'] = -4;
                $json['message'] = $query;
                //$stmt->errorInfo()[2];

                echo json_encode($json);
            }

        }

        public function lists(){
            // select all qusery
            $query = "SELECT * FROM " . $this->table_name;
			
            // prepare query statement
            $stmt = $this->conn->prepare($query);

            $stmt->execute();

            $user=array();

            while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
                array_push($user,
                array('id_patient'=>$row['id_patient'],'firstname'=>$row['firstname'],'lastname'=>$row['lastname'],
                    'age'=>$row['age']));
            }

            echo json_encode($user);
        }
       
	}
    # instaciation de la class User en passant le parametre de la base de donneé
    $user = new User($db);
?>