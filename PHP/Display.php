<?php
	if($_SERVER["REQUEST_METHOD"]=="POST"){
		global $connection;
		require 'conn.php';
		$st_date = $_POST['sdate'];
		$e_date = $_POST['edate'];
		$keyword = $_POST['keyword'];
		if(!empty($_POST['User_id']) && !empty($st_date) && !empty($e_date) && !empty($keyword))
			display($connection,$_POST['User_id'],$_POST['sdate'],$_POST['edate'],$_POST['keyword']);
	}
	else{
		echo "Please give post request";
	}
	
	function display($connection,$User_id,$start_date,$en_date,$keyword){
			$start = $start_date;
			$end = $en_date;
			$table_name = $User_id;
			$st = date_create($start);
			$en = date_create($end);
			$st_date = date_format($st,"Y-m-d");
			$end_date = date_format($en,"Y-m-d");
			$query1 = query($keyword,$table_name,$st_date,$end_date);
			$result = mysqli_query($connection,$query1);
			$datas = array();
			if($result){
				$des = "";
				while($row = mysqli_fetch_assoc($result)){
					if($keyword=="expense")
						$des = $row['Exp_Des'];
					else
						$des = $row['Inc_Des'];
					//$amt = $row['Exp_Amt'];
					if($des!="-"){
						$datas[] = $row;
					}
				}
				$json_enc = json_encode($datas);
				echo $json_enc;
			}
			else{
				echo"Error occured".mysqli_error($connection);
			}
		mysqli_close($connection);
	}
	
	
	function query($Keyword,$table_name,$start_date,$end_date){
		switch($Keyword){
			case "expense":
				$sql = "select Date,Exp_Des,Exp_Amt from $table_name where Date between '$start_date' and '$end_date'";
			break;
			
			case "income":
				$sql = "select Date,Inc_Des,Inc_Amt from $table_name where Date between '$start_date' and '$end_date'";
			break;
			
			case "Both":
				$sql = "select * from $table_name where Date between '$start_date' and '$end_date'";
			break;
			
			default:
				$sql = "incorrect keyword";
		}
		
		return $sql;
	}
?>