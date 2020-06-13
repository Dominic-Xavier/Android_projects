<?php
	if($_SERVER["REQUEST_METHOD"]=="POST"){
		global $connection;
		require 'conn.php';
		$st_date = $_POST['sdate'];
		$e_date = $_POST['edate'];
		$keyword = $_POST['keyword'];
		if(!empty($_POST['User_id']) && !empty($st_date) && !empty($e_date) && !empty($keyword))
			display($connection,$_POST['User_id'],$_POST['sdate'],$_POST['edate'],$_POST['keyword']);
		else
			//Total_Expense_Income_MonthWise($connection,$_POST['User_id'],$st_date);
		echo "Error occured";
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
					$Exp_Des = $row['Exp_Des'];
					$Inc_Des = $row['Inc_Des'];
					if($Exp_Des!="-" && $Inc_Des!="-"){
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
		if($Keyword == "expense")
			$sql = "select Date,Exp_Des,Exp_Amt from $table_name where Date between '$start_date' and '$end_date'";
		else if($Keyword == "income")
			$sql = "select Date,Inc_Des,Inc_Amt from $table_name where Date between '$start_date' and '$end_date'";
		
		return $sql;
	}
?>