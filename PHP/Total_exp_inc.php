<?php
if($_SERVER['REQUEST_METHOD']=="POST"){
	include('conn.php');
	global $connection;
	expense_income($connection);
}

function expense_income($connection){
	$month1=$month2=$month3=$month4=$month5=$month6=$month7=$month8=$month9=$month10=$month4=$month11=$month12='a';
	$jan_exp=$jan_inc=$feb_exp=$feb_inc=$feb_exp=$feb_inc=$mar_exp=$mar_inc=$apr_exp=$apr_inc=$may_exp=$may_inc=$jun_exp=$jun_inc=$jul_exp=$jul_inc=$aug_exp=$$aug_inc=$aug_inc=$sep_exp=$sep_inc=$oct_exp=$oct_inc=$nov_exp=$nov_inc=$dec_exp=$dec_inc=0;
	
	if(!empty($_POST['user_id'])){
		$user_id = $_POST['user_id'];
		$u_id_income = $user_id."income";
		$expense = array();
		$u_id_expense = $user_id."expense";
		$income = array();
		$Inc_Exp_arr = array();
		$query = "SELECT YEAR(date) AS 'year', MONTH(date) AS 'month', Exp_Amt FROM $u_id_expense";
		$sql = "SELECT YEAR(date) AS 'year', MONTH(date) AS 'month', Inc_Amt FROM $u_id_income";
		$result_exp = mysqli_query($connection,$query);
		$result_inc = mysqli_query($connection,$sql);
		if($result_exp && $result_inc){
			while($row = mysqli_fetch_assoc($result_exp) and $row1 = mysqli_fetch_assoc($result_inc)){
				$d = $row['month'];
				$exp_amt = $row['Exp_Amt'];
				$inc_amt = $row1['Inc_Amt'];
					switch($d){
					case 1:{
						$month1 = "Jan";
						$jan_exp = $jan_exp+$exp_amt;
						$jan_inc = $jan_inc+$inc_amt;
						break;
					}
					case 2:{
						$month2 = "Feb";
						$feb_exp = $feb_exp+$exp_amt;
						$feb_inc = $feb_inc+$inc_amt;
						break;
					}
					case 3:{
						$month3 = "Mar";
						$mar_exp = $mar_exp+$exp_amt;
						$mar_inc = $mar_inc+$inc_amt;
						break;
					}
					case 4:{
						$month4 = "Apr";
						$apr_exp = $apr_exp+$exp_amt;
						$apr_inc = $apr_inc+$inc_amt;
						break;
					}
					case 5:{
						$month5 = "May";
						$may_exp = $may_exp+$exp_amt;
						$may_inc = $may_inc+$inc_amt;
						break;
					}
					case 6:{
						$month6 = "June";
						$jun_exp = $jun_exp+$exp_amt;
						$jun_inc = $jun_inc+$inc_amt;
						break;
					}
					case 7:{
						$month7 = "July";
						$jul_exp = $jul_exp+$exp_amt;
						$jul_inc = $jul_inc+$inc_amt;
						break;
					}
					case 8:{
						$month8 = "Aug";
						$aug_exp = $aug_exp+$exp_amt;
						$aug_inc = $aug_inc+$inc_amt;
						break;
					}
					case 9:{
						$month9 = "Sep";
						$sep_exp = $sep_exp+$exp_amt;
						$sep_inc = $sep_inc+$inc_amt;
						break;
					}
					case 10:{
						$month10 = "Oct";
						$oct_exp = $oct_exp+$exp_amt;
						$oct_inc = $oct_inc+$inc_amt;
						break;
					}
					case 11:{
						$month11 = "Nov";
						$nov_exp = $nov_exp+$exp_amt;
						$nov_inc = $nov_inc+$inc_amt;
						break;
					}
					case 12:{
						$month12 = "Dec";
						$dec_exp = $dec_exp+$exp_amt;
						$dec_inc = $dec_inc+$inc_amt;
						break;
					}
				}
			$expense[] = $row;
			$income[] = $row1;
		}
			$Inc_Exp_arr = array_merge($expense,$income);
			$merge_json = json_encode($Inc_Exp_arr);
			echo $merge_json;
			
			$datas = array();
			
			$obj = json_decode($merge_json,true);
			foreach($obj as $key => $value){
				$month = $value['month'];
				$amt = $value['Inc_Amt'];
				$datas = array('month'=>$month,'Amount'=>$amt);
			}
			print_r($datas);
			
			/* for($i=1;$i<=12;$i++){
				
			}
			(
			'month'=>$month1,'Expense'=>$jan_exp,'income'=>$jan_inc,
			'month'=>$month2,'Expense'=>$feb_exp,'income'=>$feb_inc,
			'month'=>$month3,'Expense'=>$mar_exp,'income'=>$mar_inc,
			'month'=>$month4,'Expense'=>$apr_exp,'income'=>$apr_inc,
			'month'=>$month5,'Expense'=>$may_exp,'income'=>$may_inc,
			'month'=>$month6,'Expense'=>$jun_exp,'income'=>$jun_inc,
			'month'=>$month7,'Expense'=>$jul_exp,'income'=>$jul_inc,
			'month'=>$month8,'Expense'=>$aug_exp,'income'=>$aug_inc,
			'month'=>$month9,'Expense'=>$sep_exp,'income'=>$sep_inc,
			'month'=>$month10,'Expense'=>$oct_exp,'income'=>$oct_inc,
			'month'=>$month11,'Expense'=>$nov_exp,'income'=>$nov_inc,
			'month'=>$month12,'Expense'=>$dec_exp,'income'=>$dec_inc
			);
			$all_data = json_encode($datas);
			echo $all_data; */
		}
	}
}
	
?>