<?php

use function PHPSTORM_META\type;

    include_once 'database.php';

class Countries{
     
        // database connection and table name
        private $conn;
        private $table_name = "countries";

        // object properties
        public $id;
        public $name;
        public $shortname;
    


    public function __construct($db){
        $this->conn = $db;
    }
    
    public function createNewCountry(){
        
        $query = "INSERT INTO countries(name, shortname) VALUES(?,?)";

         // posted values
         $this->name=htmlspecialchars(strip_tags($this->name));
         $this->shortname=htmlspecialchars(strip_tags($this->shortname));
        
         // prepare query
         $stmt = $this->conn->prepare($query);

         if($stmt->execute(array($this->name,$this->shortname))){
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