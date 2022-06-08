<?php
    error_reporting(0);
    include_once '../../models/database.php';
	class DisplayCityById{
     
        // database connection and table name
        private $conn;
        private $table_name = "cities";
        private $columnIdCity = "cities_id";
        		
        // constructor with $db as database connection
        public function __construct($db){
            $this->conn = $db;
        }
		
		public function lists(){
            // select all biens 
            $query = "SELECT * FROM " . $this->table_name . " WHERE " . $this->columnIdCity . " = " .  $_GET['id'].';';
			
            // prepare query statement
            $stmt = $this->conn->prepare($query);

            //$stmt->execute(array($idCity));
            $stmt->execute();

            $biens=array();

            while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
                array_push($biens,
                array('id'=>$row['id'], 'name'=>$row['name'],'latitude'=>$row['latitude'],'longitude'=>$row['longitude']
                ,'idPays'=>$row['idPays']));
            }

            echo json_encode($biens);
        }
    
    }
	
    # instaciation de la class User en passant le parametre de la base de donneé
    $biens = new DisplayCityById($db);
	$biens->lists();
?>