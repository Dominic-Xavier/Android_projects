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
	global $connection;

	$count1 = 0;
	
	if(!empty($_POST['user']) || !empty($_POST['pass'])){
	
	$username = $_POST["user"];
	$password = $_POST["pass"];
	
	$query2 = "select * from login";
	
	if($res = mysqli_query($connection,$query2)){
		$count = mysqli_num_rows($res)+1;
		global $uid;
		$uid = "u_id".$count;
	}
	
	$query = "insert into login(id,Username,Password) values('$uid','$username','$password')";
	$sql= "create table $uid(Date date,Description varchar(250),Amount int(5))";
				while($row = mysqli_fetch_assoc($res)){
				$dbusername = $row['Username'];
				if($username == $dbusername){
					$count1 = 1;
					break;
					}
				}
				if($count1==0){
					if(mysqli_query($connection,$query)){ //inserting values into database
						if(mysqli_query($connection,$sql)){ //creating table in the name of user_id
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
		mysqli_close($connection);
	}
}

?>