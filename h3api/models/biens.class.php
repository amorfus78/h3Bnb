<?php

use function PHPSTORM_META\type;

    include_once 'database.php';

	class Bien{
     
        // database connection and table name
        private $conn;
        private $table_name = "biens";

        // object properties
        public $id;
        public $beds;
        public $latitude;
        public $longitude;
        public $type;
        public $availabilities;
        public $price;
        public $idCity;
        public $idOwner;
        public $image;


        public function __construct($db){
            $this->conn = $db;
        }
        
        public function createNewBien(){

            // photo Location
            
            $query = "INSERT INTO biens(beds, type, latitude, longitude, availabilities, description, price, idCity, idOwner, images) VALUES(?,?,?,?,?,?,?,?,?,?)";

            // posted values
            $this->beds=htmlspecialchars(strip_tags($this->beds));
            $this->type=htmlspecialchars(strip_tags($this->type));
            $this->latitude=htmlspecialchars(strip_tags($this->latitude));
            $this->longitude=htmlspecialchars(strip_tags($this->longitude));
            $this->availabilities=htmlspecialchars(strip_tags($this->availabilities));
            $this->description=htmlspecialchars(strip_tags($this->description));
            $this->price=htmlspecialchars(strip_tags($this->price));
            $this->idCity=htmlspecialchars(strip_tags($this->idCity));
            $this->idOwner=htmlspecialchars(strip_tags($this->idOwner));
           
            // prepare query
            $stmt = $this->conn->prepare($query);

            if($stmt->execute(array($this->beds,$this->type,$this->latitude,$this->longitude,$this->availabilities,$this->description,$this->price,$this->idCity,$this->idOwner,$this->image    ))){
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

            $biens=array();

            while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
                array_push($biens,
                array('id'=>$row['id'],'beds'=>$row['beds'],'type'=>$row['type'], 'price'=>$row['price'],'latitude'=>$row['latitude'],'longitude'=>$row['longitude'],
                'availabilities'=>$row['availabilities'],'description'=>$row['description'],'idCity'=>$row['idCity'], 'idOwner'=>$row['idOwner'], 'image'=>$row['images']));
            }

            echo json_encode($biens);
        }
       
	}
?>