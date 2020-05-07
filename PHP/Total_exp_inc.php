<?php
if($_SERVER['REQUEST_METHOD']=="POST"){
	include('conn.php');
	global $connection,$expense,$income;
	expense_income($connection,$expense,$income);
}

function expense_income($connection,$expense,$income){
	$data = array();
	$month;
	if(!empty($_POST['user_id'])){
		$user_id = $_POST['user_id'];
		$u_id_income = $user_id."income";
		$expense = array();
		$u_id_expense = $user_id."expense";
		$income = array();
		$query = "SELECT YEAR(date) AS 'year', MONTH(date) AS 'month', Amount FROM $u_id_expense";
		$sql = "SELECT YEAR(date) AS 'year', MONTH(date) AS 'month', Amount FROM $u_id_income";
		$result_exp = mysqli_query($connection,$query);
		$result_inc = mysqli_query($connection,$sql);
		if($result_exp && $result_inc){
			while($row = mysqli_fetch_assoc($result_exp) and $row1 = mysqli_fetch_assoc($result_inc)){
				$d = $row['month'];
				$amt = $row['Amount'];
				switch($d){
					case 1:{
						$month = "Jan";
						$jan = $jan+$amt;
						break;
					}
					case 2:{
						$month = "Feb";
						$feb = $feb+$amt;
						break;
					}
					case 3:{
						$month = "Mar";
						$mar = $mar+$amt;
						break;
					}
					case 4:{
						$month = "Apr";
						$apr = $apr+$amt;
						break;
					}
					case 5:{
						$month = "May";
						$may = $may+$amt;
						break;
					}
					case 6:{
						$month = "June";
						$june = $june+$amt;
						break;
					}
					case 7:{
						$month = "July";
						$july = $july+$amt;
						break;
					}
					case 8:{
						$month = "Aug";
						$aug = $aug+$amt;
						break;
					}
					case 9:{
						$month = "Sep";
						$sep = $sep+$amt;
						break;
					}
					case 10:{
						$month = "Oct";
						$oct = $oct+$amt;
						break;
					}
					case 11:{
						$month = "Nov";
						$nov = $nov+$amt;
						break;
					}
					case 12:{
						$month = "Dec";
						$dec = $dec+$amt;
						break;
					}
				}
				$expense[] = $row;
				$income[] = $row1;
				print_r($expense);
				print_r($income);
			}
			$expense = json_encode(['Expense'=>$expense]);
			echo $expense;
			$income = json_encode(['Income'=>$income]);
			echo $income;
		}
	}
}

function data($Income,$expense){
	
	echo $Income;
	echo $expense;
	
	
	/* $dateElements = explode('-', $dateValue);
	$year = $dateElements[0];

	echo $year;    //2012

	switch ($dateElements[1]) {

	   case '01'    :  $mo = "January";
					   break;

	   case '02'    :  $mo = "February";
					   break;

	   case '03'    :  $mo = "March";
					   break;

		 .
		 .
		 .

	   case '12'    :  $mo = "December";
					   break;


	}

	echo $mo; */
}
	
?>