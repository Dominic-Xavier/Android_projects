<?php

	require 'conn.php';
	login();
	
	function login(){
		
		global $connection,$count,$user_id;
		if(!empty($_POST['user']) && !empty($_POST['pass']) && !empty($_POST['Mobile_no'])){
			$username = $_POST["user"];
			$password = $_POST["pass"];	
			$sql="SELECT * FROM login WHERE Username='$username' and Password='$password'";
			$result = mysqli_query($connection,$sql);
			if(mysqli_num_rows($result)>0){
				while($row = mysqli_fetch_assoc($result)){
				$user_id = $row['id'];
				$dbusername=$row['Username'];  
				$dbpassword=$row['Password'];
				if($username==$dbusername && $password==$dbpassword){
					$count=true;
					break;
				}
				else{
					$count=false;
					}
				}
			}
			if($count==true){
				echo $user_id;
				//$ipaddress = getenv("REMOTE_ADDR") ; 
				//echo "Your IP Address is " . $ipaddress;
			}
			else{
				//echo"Username or password is incorrect";
				echo"User name or Password mismatch";
			}
		}
		mysqli_close($connection);
	}		
?>