<?php
	if($_SERVER["REQUEST_METHOD"]=="POST"){
		require 'conn.php';
		display();
	}
	else{
		echo "Please give post request";
	}
	
	function display(){
		global $connection;
		if(!empty($_POST['sdate']) || !empty($_POST['edate']) || !empty($_POST['User_id'])){
			$start = $_POST['sdate'];
			$end = $_POST['edate'];
			$u_id = $_POST['User_id'];
			$st = date_create($start);
			$en = date_create($end);
			$st_date = date_format($st,"Y-m-d");
			$end_date = date_format($en,"Y-m-d");
			$query = "select * from $u_id where Date between '$st_date' and '$end_date'";
			$result = mysqli_query($connection,$query);
			$datas = array();
			if($result){
				while($row = mysqli_fetch_assoc($result)){
					$datas[] = $row;
				}
				$json_enc = json_encode($datas);
				echo $json_enc;
			}
			else{
				echo"Error occured".mysqli_error($connection);
			}
		}
		else{
			echo "User_id is ank";
		}
		mysqli_close($connection);
	}
?>