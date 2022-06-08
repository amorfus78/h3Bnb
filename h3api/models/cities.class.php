<?php

use function PHPSTORM_META\type;

    include_once 'database.php';

class Cities{
     
        // database connection and table name
        private $conn;
        private $table_name = "cities";

        // object properties
        public $id;
        public $name;
        public $latitude;
        public $longitude;
        public $idPays;
    


    public function __construct($db){
        $this->conn = $db;
    }
    
    public function createNewCountry(){
        
        $query = "INSERT INTO cities(name, latitude, longitude, idPays) VALUES(?,?,?,?)";

         // posted values
         $this->name=htmlspecialchars(strip_tags($this->name));
         $this->latitude=htmlspecialchars(strip_tags($this->latitude));
         $this->longitude=htmlspecialchars(strip_tags($this->longitude));
         $this->idPays=htmlspecialchars(strip_tags($this->idPays));
        
         // prepare query
         $stmt = $this->conn->prepare($query);

         if($stmt->execute(array($this->name,$this->latitude,$this->longitude,$this->idPays))){
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

}

?>