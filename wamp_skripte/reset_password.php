<?php
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();

	$hash=$_GET['id'];
	$hsh=sha1($hash);
	$id=$db->getResetById($hsh);
	
	/*$html1="<form id='Reset' action='' method='post' accept-charset='UTF-8'>
		<fieldset >
		<legend>Reset lozinke</legend>
 
		<label for='password1' >Nova Lozinka*:</label>
		<input type='password' name='password1' id='password1'  maxlength='50' />
 
		<label for='password2' >Ponovite lozinku*:</label>
		<input type='password' name='password2' id='password2' maxlength='50' />
 
		<input type='submit' name='Reset' value='Promijeni' />
		
		

		</fieldset>
		</form>
		";*/
		
	$html1="<!DOCTYPE html>
<html>
<head>
<style>
body {
    background-color: #26ae90;
}
input {
  font-size: 20px;
  width: 50%;
  padding:8px 13px;
  border:none;
}
input.reset{
background-color:#eceef1;
color:#26ae90;
width:53%;
}
.reset:active{
border:solid;
}
.main{
font-family:helvetica;
margin: auto;
width: 60%;
padding:5%;
text-align:center;
}
h1{
font-family:helvetica;
color:white;
}
header{
margin: auto;
width: 60%;
padding:5%;
text-align:center;
}
</style>
</head>
<body>
<header>
<h1>e-imenik reset lozinke</h1>
</header>
<div class='main'>
<form id='Reset' action='' method='post' accept-charset='UTF-8'>
<input type='password' name='password1' placeholder='Nova Lozinka'>
<br><br>
<input type='password' name='password2' placeholder='Ponovite novu lozinku'>
<br><br>
<input type='submit' name='Reset' class='reset' value='Resetiraj'>
</form>
</div>
</body>
</html>";
		
$html2="<!DOCTYPE html>
<html>
<head>
<style>
body {
    background-color: #26ae90;
}
input {
  font-size: 20px;
  width: 50%;
  padding:8px 13px;
  border:none;
}
input.reset{
background-color:#eceef1;
color:#26ae90;
width:53%;
}
.reset:active{
border:solid;
}
.main{
font-family:helvetica;
margin: auto;
width: 60%;
padding:5%;
text-align:center;
}
h1{
font-family:helvetica;
color:white;
}
header{
margin: auto;
width: 60%;
padding:5%;
text-align:center;
}
</style>
</head>
<body>

<div class='main'>
<h1>Link je istekao ili nije valjan!</h1>
</div>
</body>
</html>";

$html3="proba";
	if($id!=false){
	echo $html1;
	
	if (isset($_POST['Reset']) && $_POST['Reset'] != '') {


		$lozinka1=$_POST['password1'];
		$lozinka2=$_POST['password2'];
		if(!empty($lozinka1) && !empty($lozinka2))
		
		{
		
			if($lozinka1 == $lozinka2){
			//$hsh=sha1($hash);
			$id=$db->getResetById($hsh);
			$id_ucenika=$id["Id_ucenika"];
			$db->change_password($id_ucenika, $lozinka1);
			$db->iskoristeno($hsh);
		
			header("Location: http://localhost:8000/eimenik/lozinka_promjenjena.html"); 
			exit();
			}
			
			else{
			echo "<script>alert ('Lozinke se ne polapaju, molimo pokušajte ponovno!') ;</script>";
			}
        
		}
		else{
		echo "<script>alert ('Niste unijeli obje lozinke, molimo pokušajte ponovno!') ;</script>";
		}
		}
}
else{
echo $html2;
}
?>



 