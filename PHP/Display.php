<?php
	if($_SERVER["REQUEST_METHOD"]=="POST"){
		global $connection;
		require 'conn.php';
		$st_date = $_POST['sdate'];
		$e_date = $_POST['edate'];
		if(!empty($_POST['User_id']) && !empty($st_date) && !empty($e_date))
			display($connection,$_POST['User_id'],$_POST['sdate'],$_POST['edate']);
		else
			Total_Expense_Income_MonthWise($connection,$_POST['User_id'],$st_date);
	}
	else{
		echo "Please give post request";
	}
	
	function display($connection,$User_id,$start_date,$en_date){
			$start = $start_date;
			$end = $en_date;
			$table_name = $User_id;
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
	
	function Total_Expense_Income_MonthWise($connection,$user_id,$start_date){
		$s_date = $start_date.'-01'; //Start Date
		$Year_Month = explode("-",$s_date);
		$year = $Year_Month[0];
		$Month = $Year_Month[1];
		$end_date = $year.'-'.$Month.'-31'; //End Date
		$query = "select * from $user_id where Date between '$s_date' and '$end_date'";
		$result = mysqli_query($connection,$query);
			$datas = array();
			if($result){
				while($row = mysqli_fetch_assoc($result)){
					$des = $row['Exp_Des'];
					$amt = $row['Exp_Amt'];
					if($des!="-" ){
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
?>