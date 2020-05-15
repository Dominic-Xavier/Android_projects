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
		if(!empty($_POST['User_id']) && !empty($_POST['sdate']) && !empty($_POST['edate'])){
			$start = $_POST['sdate'];
			$end = $_POST['edate'];
			$table_name = $_POST['User_id'];
			$st = date_create($start);
			$en = date_create($end);
			$st_date = date_format($st,"Y-m-d");
			$end_date = date_format($en,"Y-m-d");
			$query = "select Date,Exp_Des,Exp_Amt from $table_name where Date between '$st_date' and '$end_date'";
			$result = mysqli_query($connection,$query);
			$datas = array();
			if($result){
				while($row = mysqli_fetch_assoc($result)){
					$des = $row['Exp_Des'];
					if($des!=null){
						$datas[] = $row;
					}
				}
				$json_enc = json_encode($datas);
				echo $json_enc;
			}
			else{
				echo"Error occured".mysqli_error($connection);
			}
		}
		else{
			$err  = "User_id is ank";
			$error = json_encode($err);
			echo $error;
		}
		mysqli_close($connection);
	}
?>