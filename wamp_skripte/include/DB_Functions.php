<?php

 
class DB_Functions {
 
    //private $db;
	public $db;
 
    //put your code here
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }
 
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($ime, $korisnicko_ime, $password) {
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
       /* $result = mysqli_query($GLOBALS["___mysqli_ston"], "INSERT INTO ucenici(Unique_id, Ime, Korisnicko_ime, Lozinka, Salt) VALUES('$uuid', '$name', '$email', '$encrypted_password', '$salt')"); */
		$result = mysqli_query($GLOBALS["___mysqli_ston"], "INSERT INTO ucenici(Ime, Korisnicko_ime, Lozinka, Salt) VALUES('$ime', '$korisnicko_ime', '$encrypted_password', '$salt')");
        // check for successful store
        if ($result) {
            // get user details 
            $uid = ((is_null($___mysqli_res = mysqli_insert_id($GLOBALS["___mysqli_ston"]))) ? false : $___mysqli_res); // last inserted id
            $result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT * FROM ucenici WHERE Id_ucenika = $uid");
            // return user details
            return mysqli_fetch_array($result);
        } else {
            return false;
        }
    }
 
    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($korisnicko_ime, $password) {
		mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
        $result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT * FROM ucenici WHERE Korisnicko_ime = '$korisnicko_ime'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
        // check for result 
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysqli_fetch_array($result);
            $salt = $result['Salt'];
            $encrypted_password = $result['Lozinka'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $result;
            }
        } else {
            // user not found
            return false;
        }
    }
	
	public function get_upis($id_var){
		mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
		$result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT upis.* FROM upis JOIN ucenici ON upis.Id_ucenika=ucenici.Id_ucenika WHERE upis.Id_ucenika = '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
		$result = mysqli_fetch_array($result);
		return $result;
	}
	
	public function get_razredi($id_var){
		mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
		$result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT razredi.* FROM razredi JOIN upis ON razredi.Id_razreda=upis.Id_razreda JOIN ucenici ON upis.Id_ucenika=ucenici.Id_ucenika WHERE ucenici.Id_ucenika = '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
		$result = mysqli_fetch_array($result);
		return $result;
	}
	
	public function get_upisani_predmeti($id_var){
		mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
		$result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT upisani_predmeti.* FROM upisani_predmeti JOIN upis ON upisani_predmeti.Id_upisa=upis.Id_upisa JOIN ucenici ON upis.Id_ucenika=ucenici.Id_ucenika WHERE ucenici.Id_ucenika = '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
		$result = mysqli_fetch_array($result);
		return $result;
	}
	
	public function get_predmeti($id_var){
		mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
		$result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT predmeti.* FROM predmeti JOIN upisani_predmeti ON predmeti.Id_predmeta=upisani_predmeti.Id_predmeta JOIN upis ON upisani_predmeti.Id_upisa=upis.Id_upisa JOIN ucenici ON upis.Id_ucenika=ucenici.Id_ucenika WHERE ucenici.Id_ucenika = '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
		$result = mysqli_fetch_array($result);
		return $result;
	}
	
	public function get_nastavnici($id_var){
		mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
		$result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT nastavnici.* FROM nastavnici JOIN raz_pred_nast ON nastavnici.Id_nastavnika=raz_pred_nast.Id_nastavnika JOIN razredi ON raz_pred_nast.Id_razreda=razredi.Id_razreda JOIN upis ON razredi.Id_razreda=upis.Id_razreda JOIN ucenici ON upis.Id_ucenika=ucenici.Id_ucenika WHERE ucenici.Id_ucenika = '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
		$result = mysqli_fetch_array($result);
		return $result;
	}
	
	public function get_raz_pred_nast($id_var){
		mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
		$result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT raz_pred_nast.* FROM raz_pred_nast JOIN razredi ON raz_pred_nast.Id_razreda=razredi.Id_razreda JOIN upis ON razredi.Id_razreda=upis.Id_razreda JOIN ucenici ON upis.Id_ucenika=ucenici.Id_ucenika WHERE ucenici.Id_ucenika = '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
		$result = mysqli_fetch_array($result);
		return $result;
	}
	
	public function get_ocjene($id_var){
		mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
		$result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT ocjene.* FROM ocjene WHERE id_ucenika= '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
		$result = mysqli_fetch_array($result);
		return $result;

	}
	
	public function get_rubrike($id_var){
		mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
		$result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT DISTINCT rubrike.* FROM rubrike JOIN ocjene ON rubrike.Id_rubrike=ocjene.Id_rubrike JOIN ucenici ON ocjene.Id_ucenika=ucenici.Id_ucenika WHERE ucenici.Id_ucenika = '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
		$result = mysqli_fetch_array($result);
		return $result;
		
	}
	
	public function get_komentar($id_var){
		mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
		$result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT komentar.* FROM komentar JOIN ucenici ON komentar.Id_ucenika=ucenici.Id_ucenika WHERE ucenici.Id_ucenika = '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
		$result = mysqli_fetch_array($result);
		return $result;
	}
		
	
	
	
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($korisnicko_ime) {
        $result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT Korisnicko_ime from ucenici WHERE Korisnicko_ime = '$korisnicko_ime'");
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed 
            return true;
        } else {
            // user not existed
            return false;
        }
    }
 
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
 
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }
	
	public function change_password($id_ucenika, $password) {
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
       /* $result = mysqli_query($GLOBALS["___mysqli_ston"], "INSERT INTO ucenici(Unique_id, Ime, Korisnicko_ime, Lozinka, Salt) VALUES('$uuid', '$name', '$email', '$encrypted_password', '$salt')"); */
		$result = mysqli_query($GLOBALS["___mysqli_ston"], "UPDATE ucenici SET Lozinka='$encrypted_password', Salt='$salt' WHERE Id_Ucenika='$id_ucenika'");
		
		if ($result) {
			echo "<script>alert ('Lozinka uspješno resetirana!') ;</script>";
        } else {
            echo "<script>alert ('Lozinka nije uspješno resetirana!') ;</script>";
        }
    }
	
	public function getUserByEmail($email) {
		mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
        $result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT * FROM ucenici WHERE Email = '$email'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
        // check for result 
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysqli_fetch_array($result);
                return $result;
            }
        else {
            // user not found
			$response["error"] = TRUE;
                $response["error_msg"] = "Nije pronađen korisnik!";
                echo json_encode($response);
            return false;
        }
    }
	
	public function ResetPassword($hash_before, $hash_after, $id_uc) {
       /* $result = mysqli_query($GLOBALS["___mysqli_ston"], "INSERT INTO ucenici(Unique_id, Ime, Korisnicko_ime, Lozinka, Salt) VALUES('$uuid', '$name', '$email', '$encrypted_password', '$salt')"); */
		$result = mysqli_query($GLOBALS["___mysqli_ston"], "INSERT INTO promjena_lozinke (Id, Id_ucenika, Iskoristeno) VALUES ('$hash_after', '$id_uc', '0')")or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
        // check for successful store
        if ($result) {
		$response["error"] = FALSE;
		$response["promjena"]["hash"]=$hash_before;
		echo json_encode($response);
        } else {
        $response["error"] = TRUE;
        $response["error_msg"] = "Greška!";
        }
    }
	
	public function getResetById($id) {
		mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
        $result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT * FROM promjena_lozinke WHERE Id = '$id' AND Iskoristeno = 0 AND Vrijeme > DATE_SUB(NOW(), INTERVAL 1 DAY)") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysqli_fetch_array($result);
                return $result;
            }
        else {
                //echo "<script>alert ('Link nije ispravan ili je istekao!') ;</script>";
            return false;
        }
    }
	
	public function iskoristeno($hash) {
		$result = mysqli_query($GLOBALS["___mysqli_ston"], "UPDATE promjena_lozinke SET Iskoristeno='1'WHERE Id='$hash'");
		
		if ($result) {
			//echo "<script>alert ('Uspješno iskorišteno!') ;</script>";
        } else {
            //echo "<script>alert ('Neuspješno iskorišteno!') ;</script>";
        }
    }
	

	
 
}
 
?>