<?php

if($_SERVER["REQUEST_METHOD"]=="POST")
{
	require 'conn.php';
	register();
}
else{
	echo "Please give post request";
}

function register()
{
	global $connection,$uid;

	$count1 = $count = 0;
	
	if(!empty($_POST['user']) || !empty($_POST['pass']) || !empty($_POST['Mobile_no'])){
	
	$username = $_POST["user"];
	$password = $_POST["pass"];
	$Mobile_Number = $_POST["Mobile_no"];
	
	$query2 = "select * from login";
	
	if($res = mysqli_query($connection,$query2)){
		$count = mysqli_num_rows($res)+1;
		
		$uid = "u_id_".$count;
	}
	
	$query = "insert into login(id,Username,Password,Mobile_Number) values('$uid','$username','$password','$Mobile_Number')";//used to store user's login credential
	$sql= "create table $uid(Date date, Exp_Des text(5000),Exp_Amt int(10),Inc_Des text(5000),Inc_Amt int(10))";//Used to store user information
		while($row = mysqli_fetch_assoc($res)){
		$dbusername = $row['Username'];
		if($username == $dbusername){
			$count1 = 1;
			break;
			}
		}
		if($count1==0){
			if(mysqli_query($connection,$sql)){ // Creating Table for users
				if(mysqli_query($connection,$query)){// Inserting values into database
					echo "Registered Successfully";
				}
			}         
		}
		else if($count1==1){
			echo "Sorry!, Username already Exists";
		}
		else{
			echo"Failure".mysqli_error($connection);
		}
		$count1 = 0;
	}
	
	mysqli_close($connection);
}

?>