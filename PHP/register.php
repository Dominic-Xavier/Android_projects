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
	
	if(!empty($_POST['user']) || !empty($_POST['pass']) || !empty($_POST['Mobile_no'])){
	
	$username = $_POST["user"];
	$password = $_POST["pass"];
	$Mobile_Number = $_POST["Mobile_no"];
	
	$query2 = "select * from login";
	
	if($res = mysqli_query($connection,$query2)){
		$count = mysqli_num_rows($res)+1;
		global $uid;
		$uid = "u_id_".$count;
	}
	
	$u_id_income = $uid."income";
	$u_id_expense = $uid."expense";
	
	$query = "insert into login(id,Username,Password,Mobile_Number) values('$uid','$username','$password','$Mobile_Number')";
	$sql= "create table $u_id_income(Date date,Description varchar(250),Amount int(10))";
	$sqls= "create table $u_id_expense(Date date,Description varchar(250),Amount int(10))";
				while($row = mysqli_fetch_assoc($res)){
				$dbusername = $row['Username'];
				if($username == $dbusername){
					$count1 = 1;
					break;
					}
				}
				if($count1==0){
					if(mysqli_query($connection,$query)){ //inserting values into database
						if(mysqli_query($connection,$sql)){ //creating table in the name of u_id_income
							if(mysqli_query($connection,$sqls)){ //creating table in the name of u_id_expense
								echo "Registered Successfully";
							}
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