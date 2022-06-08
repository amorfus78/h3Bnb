<?php
    error_reporting(0);
    include_once '../../models/database.php';
	class DisplayCountry{
     
        // database connection and table name
        private $conn;
        private $table_name = "countries";
		
        // constructor with $db as database connection
        public function __construct($db){
            $this->conn = $db;
        }
		
		public function lists(){
            // select all qusery
            $query = "SELECT * FROM " . $this->table_name;
			
            // prepare query statement
            $stmt = $this->conn->prepare($query);

            $stmt->execute();

            $country=array();

            while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
                array_push($country,
                array('id'=>$row['id'],'name'=>$row['name'],'shortname'=>$row['shortname'] ));
            }

            echo json_encode($country);
        }
    
    }
	
    # instaciation de la class User en passant le parametre de la base de donneé
    $patients = new DisplayCountry($db);
	$patients->lists();
?>