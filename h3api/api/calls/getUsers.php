<?php
    error_reporting(0);
    include_once '../../models/database.php';
    //include_once '../../models/user.class.php'
	class DisplayUser{
     
        // database connection and table name
        private $conn;
        private $table_name = "user";
		
        // constructor with $db as database connection
        public function __construct($db){
            $this->conn = $db;
        }
		
		public function lists(){
            // select all users
            $query = "SELECT * FROM " . $this->table_name;
			
            // prepare query statement
            $stmt = $this->conn->prepare($query);

            $stmt->execute();

            $user=array();

            while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
                array_push($user,
                array('id'=>$row['id'],'fname'=>$row['fname'],'lname'=>$row['lname'],
                    'email'=>$row['email'],'password'=>$row['password'],'phone'=>$row['phone'] ));
            }

            echo json_encode($user);
        }
    
    }
	
    # instaciation de la class User en passant le parametre de la base de donneé
    $users = new DisplayUser($db);
	$users->lists();
?>