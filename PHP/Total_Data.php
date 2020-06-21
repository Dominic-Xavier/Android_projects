<?php

	if($_SERVER['REQUEST_METHOD']=="POST"){
		require('conn.php');
		global $connection;
		if(!empty($_POST['User_id']) && !empty($_POST['sdate'] && !empty($_POST['edate'])))
			Total_Expense_Income_MonthWise($connection,$_POST['User_id'],$_POST['sdate'],$_POST['edate']);
		else
			echo "Blank";
	}
	else{
		echo "Please ive Post request";
	}
	
	function Total_Expense_Income_MonthWise($connection,$User_id,$start_date,$en_date){
			$start = $start_date;
			$end = $en_date;
			$table_name = $User_id;
			$st = date_create($start);
			$en = date_create($end);
			$st_date = date_format($st,"Y-m-d");
			$end_date = date_format($en,"Y-m-d");
			$sql = "select * from $table_name where Date between '$st_date' and '$end_date'";
			$result = mysqli_query($connection,$sql);
			$datas = array();
			if($result){
				while($row = mysqli_fetch_assoc($result)){
						$Exp = $row['Exp_Des'];
						$Inc = $row['Inc_Des'];
						
					if($Inc!='-')
						$datas[] = $row;
					else if($Exp!='-')
						$datas[] = $row;
				}
				$json_enc = json_encode($datas);
				echo $json_enc;
			}
			else{
				echo"Error occured".mysqli_error($connection);
			}
		mysqli_close($connection);
	}
?>

