<?php
session_start();

/**
 * File to handle all API requests
 * Accepts GET and POST
 * 
 * Each request will be identified by TAG
 * Response will be JSON data
 
  /**
 * check for POST request 
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];
 
    // include db handler
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();
 
    // response Array
    $response = array("tag" => $tag, "error" => FALSE);
 
    // check for tag type
    if ($tag == 'login') {
        // Request type is check Login
        $korisnicko_ime = $_POST['korisnicko_ime'];
        $password = $_POST['password'];
 
        // check for user
		
			$user = $db->getUserByEmailAndPassword($korisnicko_ime, $password);
			$id_var=$user["Id_ucenika"];
			$_SESSION["id_var"]=$id_var;
			$upis = $db->get_upis($id_var);
			$razredi = $db->get_razredi($id_var);
			//$upisani_predmeti = $db->get_upisani_predmeti($id_var);
			//$predmeti = $db->get_predmeti($id_var);
			$nastavnici = $db->get_nastavnici($id_var);
			$raz_pred_nast = $db->get_raz_pred_nast($id_var);
			//$ocjene = $db->get_ocjene($id_var);
			//$rubrike = $db->get_rubrike($id_var);
			//$komentar = $db->get_komentar($id_var);
		
        if ($user != false) {
            // user found
            $response["error"] = FALSE;
            /*$response["uid"] = $user["Unique_id"];*/
			
			$result_ocjene=mysqli_query($GLOBALS["___mysqli_ston"], "SELECT ocjene.* FROM ocjene WHERE id_ucenika= '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
			$result_rubrike = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT DISTINCT rubrike.* FROM rubrike JOIN ocjene ON rubrike.Id_rubrike=ocjene.Id_rubrike JOIN ucenici ON ocjene.Id_ucenika=ucenici.Id_ucenika WHERE ucenici.Id_ucenika = '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
			$result_komentar = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT komentar.* FROM komentar JOIN ucenici ON komentar.Id_ucenika=ucenici.Id_ucenika WHERE ucenici.Id_ucenika = '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
			$result_predmeti = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT predmeti.* FROM predmeti JOIN upisani_predmeti ON predmeti.Id_predmeta=upisani_predmeti.Id_predmeta JOIN upis ON upisani_predmeti.Id_upisa=upis.Id_upisa JOIN ucenici ON upis.Id_ucenika=ucenici.Id_ucenika WHERE ucenici.Id_ucenika = '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
			$result_upisani_predmeti = mysqli_query($GLOBALS["___mysqli_ston"], "SELECT upisani_predmeti.* FROM upisani_predmeti JOIN upis ON upisani_predmeti.Id_upisa=upis.Id_upisa JOIN ucenici ON upis.Id_ucenika=ucenici.Id_ucenika WHERE ucenici.Id_ucenika = '$id_var'") or die(((is_object($GLOBALS["___mysqli_ston"])) ? mysqli_error($GLOBALS["___mysqli_ston"]) : (($___mysqli_res = mysqli_connect_error()) ? $___mysqli_res : false)));
			
			$response["user"]["id_ucenika"] = $user["Id_ucenika"];
            $response["user"]["ime"] = $user["Ime"];
			$response["user"]["prezime"] = $user["Prezime"];
            $response["user"]["korisnicko_ime"] = $user["Korisnicko_ime"];
			
			$response["upis"]["id_upisa"] = $upis["Id_upisa"];
			$response["upis"]["id_ucenika"] = $upis["Id_ucenika"];
			$response["upis"]["id_razreda"] = $upis["Id_razreda"];
			$response["upis"]["datum_upisa"] = $upis["Datum_upisa"];
			
			$response["razredi"]["id_razreda"] = $razredi["Id_razreda"];
			$response["razredi"]["godina"] = $razredi["Godina"];
			$response["razredi"]["razred"] = $razredi["Razred"];
			$response["razredi"]["godina_upisa"] = $razredi["Godina_upisa"];
			
			$response["upisani_predmeti"]=array();
			while($row=mysqli_fetch_array($result_upisani_predmeti)){
			$upisani_predmeti=array();
			$upisani_predmeti["redni_br_upisa"] = $row["Redni_br_upisa"];
			$upisani_predmeti["id_upisa"] = $row["Id_upisa"];
			$upisani_predmeti["id_predmeta"] = $row["Id_predmeta"];
			$upisani_predmeti["datum_upisa"] = $row["Datum_upisa"];
			$upisani_predmeti["zavrsna_ocjena_predmeta"] = $row["Zavrsna_ocjena_predmeta"];
			$upisani_predmeti["datum_zavrsne_ocjene"] = $row["Datum_zavrsne_ocjene"];
			
			array_push($response["upisani_predmeti"], $upisani_predmeti);
			}
			
			$response["predmeti"]=array();
			while($row=mysqli_fetch_array($result_predmeti)){
			$predmeti=array();
			$predmeti["id_predmeta"] = $row["Id_predmeta"];
			$predmeti["naziv_predmeta"] = $row["Naziv_predmeta"];
			
			
			array_push($response["predmeti"], $predmeti);
			}

			$response["nastavnici"]["id_nastavnika"] = $nastavnici["Id_nastavnika"];
			$response["nastavnici"]["oib_nastavnika"] = $nastavnici["Oib_nastavnika"];
			$response["nastavnici"]["ime"] = $nastavnici["Ime"];
			$response["nastavnici"]["prezime"] = $nastavnici["Prezime"];
			
			$response["raz_pred_nast"]["id_razreda"] = $raz_pred_nast["Id_razreda"];
			$response["raz_pred_nast"]["id_nastavnika"] = $raz_pred_nast["Id_nastavnika"];
			$response["raz_pred_nast"]["redni_br_upisa"] = $raz_pred_nast["Redni_br_upisa"];
			$response["raz_pred_nast"]["datum_od"] = $raz_pred_nast["Datum_od"];
			$response["raz_pred_nast"]["datum_do"] = $raz_pred_nast["Datum_do"];
			
			
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
			
			$response["rubrike"]=array();
			while($row=mysqli_fetch_array($result_rubrike)){
			$rubrike=array();
			$rubrike["id_rubrike"] = $row["Id_rubrike"];
			$rubrike["naziv_rubrike"] = $row["Naziv_rubrike"];
			
			array_push($response["rubrike"], $rubrike);
			}
			
			$response["komentar"]=array();
			while($row=mysqli_fetch_array($result_komentar)){
			$komentar=array();
			$komentar["id_komentara"] = $row["Id_komentara"];
			$komentar["id_nastavnika"] = $row["Id_nastavnika"];
			$komentar["id_ucenika"] = $row["Id_ucenika"];
			$komentar["redni_br_upisa"] = $row["Redni_br_upisa"];
			$komentar["datum"] = $row["Datum"];
			$komentar["komentar"] = $row["Komentar"];
			
			array_push($response["komentar"], $komentar);
			}
				
			
			
			
           echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = TRUE;
            $response["error_msg"] = "Netočno korisničko ime ili lozinka!";
            echo json_encode($response);
        }
    } else if ($tag == 'register') {
        // Request type is Register new user
        $ime = $_POST['ime'];
        $korisnicko_ime = $_POST['korisnicko_ime'];
        $password = $_POST['password'];
 
        // check if user is already existed
        if ($db->isUserExisted($ime)) {
            // user is already existed - error response
            $response["error"] = TRUE;
            $response["error_msg"] = "Korisnik već postoji!";
            echo json_encode($response);
        } else {
            // store user
            $user = $db->storeUser($ime, $korisnicko_ime, $password);
            if ($user) {
                // user stored successfully
                $response["error"] = FALSE;
                /*$response["uid"] = $user["Unique_id"];*/
                $response["user"]["ime"] = $user["Ime"];
                $response["user"]["korisnicko_ime"] = $user["Korisnicko_ime"];
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = TRUE;
                $response["error_msg"] = "Error occured in Registration";
                echo json_encode($response);
            }
        }
    } else {
        // user failed to store
        $response["error"] = TRUE;
        $response["error_msg"] = "Unknow 'tag' value. It should be either 'login' or 'register'";
        echo json_encode($response);
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter 'tag' is missing!";
    echo json_encode($response);
}

?>