<?php
    error_reporting(0);
    include_once '../../models/database.php';
	class DisplayBien{
     
        // database connection and table name
        private $conn;
           private $table_name = "biens";
        private $columnIdUser = "idOwner";

		
        // constructor with $db as database connection
        public function __construct($db){
            $this->conn = $db;
        }
		
		public function lists(){
            // select all qusery
            //$query = "SELECT * FROM ". $this->table_name . " WHERE " . $this->columnIdUser . " = " . $_GET["idOwner"].";";
            $query ="SELECT * FROM ( biens INNER JOIN cities ON cities.cities_id = biens.idCity) INNER JOIN contrats ON contrats.idBien = biens.id WHERE contrats.idUser=biens.idOwner;"
			
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
	
    # instaciation de la class User en passant le parametre de la base de donneé
    $biens = new DisplayBien($db);
	$biens->lists();
   
?>  