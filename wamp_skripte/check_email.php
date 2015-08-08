<?php

if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];
 
    // include db handler
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();
 
    // response Array
    $response = array("tag" => $tag, "error" => FALSE);
	
	if ($tag == 'check_email') {
	
	$email = $_POST['mail'];
	
	mysqli_query($GLOBALS["___mysqli_ston"], 'SET CHARACTER SET utf8');
        //$result = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT * FROM ucenici WHERE Email = '$email'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
        // check for result 
        
		$user = $db->getUserByEmail($email);
		
		$hash_before=uniqid();
		$hash_after=sha1($hash_before);
		$id_uc=$user["Id_ucenika"];
		$db->ResetPassword($hash_before, $hash_after, $id_uc);
		/*$res=mysqli_query($GLOBALS["___mysqli_ston"],"INSERT INTO promjena_lozinke (Id, Id_ucenika) VALUES ('$hash', '$id_uc')") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
		if($res){
		$response["error"] = FALSE;
		$response["promjena"]["hash"]=$hash;
		echo json_encode($response);
		}
		else{
		$response["error"] = TRUE;
        $response["error_msg"] = "Greška!";
		}
		
		
		echo json_encode($response);
		}
		else{
				$response["error"] = TRUE;
                $response["error_msg"] = "Nije pronađen korisnik!";
                echo json_encode($response);
		}*/
	
	}else {
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