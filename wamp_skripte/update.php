<?php

if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];
 
    // include db handler
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();
	
    $response = array("tag" => $tag, "error" => FALSE);
	
	if ($tag == 'update') {
	
	$id_var=$_POST["id_var"];
	$datum_var=$_POST["datum"];
	$result_ocjene=mysqli_query($GLOBALS["___mysqli_ston"], "SELECT * FROM ocjene WHERE id_ucenika='$id_var' AND datum_ocjene > '$datum_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));

	$response["ocjene"]=array();
			while($row=mysqli_fetch_array($result_ocjene)){
			$ocjene=array();
			$ocjene["redni_broj_ocjene"] = $row["Redni_broj_ocjene"];
			$ocjene["id_ucenika"] = $row["Id_ucenika"];
			$ocjene["id_rubrike"] = $row["Id_rubrike"];
			$ocjene["redni_br_upisa"] = $row["Redni_br_upisa"];
			$ocjene["ocjena"] = $row["Ocjena"];
			$ocjene["datum_ocjene"] = $row["Datum_ocjene"];
			$ocjene["komentar"] = $row["Komentar"];
			
			array_push($response["ocjene"], $ocjene);
			}
	echo json_encode($response);
	}
	
	else {
        // user failed to store
        $response["error"] = TRUE;
        $response["error_msg"] = "Unknow 'tag' value. It should be update";
        echo json_encode($response);
    }
}

else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter 'tag' is missing!";
    echo json_encode($response);
}

?>