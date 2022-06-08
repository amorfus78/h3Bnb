<?php

use function PHPSTORM_META\type;

    include_once 'database.php';

	class Contrat{
     
        // database connection and table name
        private $conn;
        private $table_name = "contrats";

        // object properties
        public $id;
        public $dates;
        public $chat;
        public $idBien;
        public $idUser;


        public function __construct($db){
            $this->conn = $db;
        }
        
        public function createNewContrat(){
            
            $query = "INSERT INTO contrats(dates, chat, idBien, idUser) VALUES(?,?,?,?)";




            // posted values
            $this->dates=htmlspecialchars(strip_tags($this->dates));
            $this->chat=htmlspecialchars(strip_tags($this->chat));
            $this->idBien=htmlspecialchars(strip_tags($this->idBien));
            $this->idUser=htmlspecialchars(strip_tags($this->idUser));                
           
            // prepare query
            $stmt = $this->conn->prepare($query);

            if($stmt->execute(array($this->dates,$this->chat,$this->idBien,$this->idUser))){
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

            $contrat=array();

            while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
                array_push($contrat,
                array('id'=>$row['id'],'idUser'=>$row['idUser'],'idBien'=>$row['idBien'],
                    'dates'=>$row['dates']));
            }

            echo json_encode($contrat);
        }
       
	}
    # instaciation de la class User en passant le parametre de la base de donneé
    $contrat = new Contrat($db);
?>