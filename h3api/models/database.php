<?php
ob_start();
session_start();
class Database{
     
    // specify your own database credentials
    // private $host = "localhost";
    // private $db_name = "h3bnbbdd";
    // private $username ="etienne";
    // private $password = "";
    // private $port = "3306";
    private $host = "localhost";
    private $db_name = "h3bnbbdd";
    private $username ="etienne";
    private $password = "";
    private $port = "3306";
    public $conn;
     
    // get the database connection
    public function getConnection(){
     
        $this->conn = null;
         
        try{
            $this->conn = new PDO("mysql:host=" . $this->host . ";port=". $this->port.";dbname=" . $this->db_name.";charset=utf8", $this->username, $this->password);
        }catch(PDOException $exception){
            echo "Connection error: " . $exception->getMessage();
        }
         
        return $this->conn;
    }
}
$database = new Database();
$db = $database->getConnection();
?>