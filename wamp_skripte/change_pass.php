<?php


 
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];
 
    // include db handler
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();
 
    // response Array
    $response = array("tag" => $tag, "error" => FALSE);
	
if ($tag == 'change_password') {
        // Request type is check Login
        $id_ucenika = $_POST['id_ucenika'];
        $password = $_POST['nova_lozinka'];
		$old_password=$_POST['stara_lozinka'];
		

		mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
        $result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT * FROM ucenici WHERE Id_ucenika = '$id_ucenika'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
        // check for result 
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysqli_fetch_array($result);
            $salt = $result['Salt'];
            $encrypted_password = $result['Lozinka'];
            $hash = $db->checkhashSSHA($salt, $old_password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                $ps=$db->change_password($id_ucenika, $password);

                $response["error"] = FALSE;
				echo json_encode($response);
				
            }else
			$response["error"] = TRUE;
                $response["error_msg"] = "Niste unjeli ispravnu staru lozinku! Molimo pokušajte ponovno! salt:" .$salt . " pass: " . $encrypted_password. " hash: " . $hash;
                echo json_encode($response);
		
		}else{
		$response["error"] = TRUE;
                $response["error_msg"] = "Nije pronađen korisnik!";
                echo json_encode($response);
		}
	}
	else {
        // user failed to store
        $response["error"] = TRUE;
        $response["error_msg"] = "Unknow 'tag' value. It should be either change_password";
        echo json_encode($response);
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter 'tag' is missing!";
    echo json_encode($response);
}


?>
