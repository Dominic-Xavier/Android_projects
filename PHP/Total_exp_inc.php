<?php
if($_SERVER['REQUEST_METHOD']=="POST"){
	include('conn.php');
	global $connection;//This valeable has database connection details
	expense_income($connection);
}

function expense_income($connection){
	
	if(!empty($_POST['User_id']) && !empty($_POST['sdate']) && !empty($_POST['edate']) && !empty($_POST['keyword'])){
		$user_id = $_POST['User_id'];
		$Inc_Exp_arr = array();
		$sql = "select year (date) as year, month (date) as month,  sum(Inc_amt) as Total_income, sum(Exp_Amt) as Total_Expense FROM $user_id group by year(date), month(date) ";
		$result = mysqli_query($connection,$sql);
			if($result){
				while($row = mysqli_fetch_assoc($result)){
					//if($row!=null)
				$Inc_Exp_arr[] = $row;
			}
			$obj = json_encode($Inc_Exp_arr);
			echo $obj;
		}
	}
}
	
?>