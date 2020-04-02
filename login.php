<?php
	session_start();
	require 'conn.php';
	login();
	
	function login(){
		
		global $connection,$count,$user_id;
		if(!empty($_POST['user']) && !empty($_POST['pass'])){
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
					$_SESSION['u_id'] = $user_id;
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
			}
			else{
				echo"Username or password is incorrect";
			}
		}
		mysqli_close($connection);
	}		
?>