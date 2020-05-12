<?php
if($_SERVER['REQUEST_METHOD']=="POST"){
	include('conn.php');
	global $connection;//This valeable has database connection details
	expense_income($connection);
}

function expense_income($connection){
	$month1=$month2=$month3=$month4=$month5=$month6=$month7=$month8=$month9=$month10=$month4=$month11=$month12=$month_p="";
	$exp1=$inc1=$exp2=$inc2=$exp3=$inc3=$exp4=$inc4=$exp5=$inc5=$exp6=$inc6=$exp7=$inc7=$exp8=$inc8=$exp9=$inc9=$exp10=$inc10=$exp11=$inc11=$exp12=$inc12=$exp_p=$inc_p=0;
	
	if(!empty($_POST['User_id'])&&!empty($_POST['sdate'])&&!empty($_POST['edate'])){
		$user_id = $_POST['User_id'];
		$u_id_income = $user_id."income";
		$expense = array();
		$u_id_expense = $user_id."expense";
		$income = array();
		$Inc_Exp_arr = array();
		$query = "SELECT YEAR(date) AS 'year', MONTH(date) AS 'month', Exp_Amt FROM $u_id_expense";//Expense table
		$sql = "SELECT YEAR(date) AS 'year', MONTH(date) AS 'month', Inc_Amt FROM $u_id_income";//Income table
		$result_exp = mysqli_query($connection,$query);
		$result_inc = mysqli_query($connection,$sql);
		if($result_exp && $result_inc){
			while($row = mysqli_fetch_assoc($result_exp) and $row1 = mysqli_fetch_assoc($result_inc)){
				$d = $row['month'];
				$exp_amt = $row['Exp_Amt'];
				$inc_amt = $row1['Inc_Amt'];
					switch($d){ //trying to figure out month wise details using month
					case 1:{
						$month1 = "Jan";
						$exp1 = $exp1+$exp_amt;
						$inc1 = $inc1+$inc_amt;
						break;
					}
					case 2:{
						$month2 = "Feb";
						$exp2 = $exp2+$exp_amt;
						$inc2 = $inc2+$inc_amt;
						break;
					}
					case 3:{
						$month3 = "Mar";
						$exp3 = $exp3+$exp_amt;
						$inc3 = $inc3+$inc_amt;
						break;
					}
					case 4:{
						$month4 = "Apr";
						$exp4 = $exp4+$exp_amt;
						$inc4 = $inc4+$inc_amt;
						break;
					}
					case 5:{
						$month5 = "May";
						$exp5 = $exp5+$exp_amt;
						$inc5 = $inc5+$inc_amt;
						break;
					}
					case 6:{
						$month6 = "June";
						$exp6 = $exp6+$exp_amt;
						$inc6 = $inc6+$inc_amt;
						break;
					}
					case 7:{
						$month7 = "July";
						$exp7 = $exp7+$exp_amt;
						$inc7 = $inc7+$inc_amt;
						break;
					}
					case 8:{
						$month8 = "Aug";
						$exp8 = $exp8+$exp_amt;
						$inc8 = $inc8+$inc_amt;
						break;
					}
					case 9:{
						$month9 = "Sep";
						$exp9 = $exp9+$exp_amt;
						$inc9 = $inc9+$inc_amt;
						break;
					}
					case 10:{
						$month10 = "Oct";
						$exp10 = $exp10+$exp_amt;
						$inc10 = $inc10+$inc_amt;
						break;
					}
					case 11:{
						$month11 = "Nov";
						$exp11 = $exp11+$exp_amt;
						$inc11 = $inc11+$inc_amt;
						break;
					}
					case 12:{
						$month12 = "Dec";
						$exp12 = $exp12+$exp_amt;
						$inc12 = $inc12+$inc_amt;
						break;
					}
				}
			$expense[] = $row;
			$income[] = $row1;
		}
			$Inc_Exp_arr = array_merge($expense,$income);
			$merge_json = json_encode($Inc_Exp_arr);
			//echo $merge_json;
			
			//Output of $merge_json
			//$merge_json = [{"year":"2020","month":"5","Exp_Amt":"23"},{"year":"2020","month":"5","Exp_Amt":"60"},{"year":"0","month":"0","Exp_Amt":"23"},{"year":"0","month":"0","Exp_Amt":"56"},{"year":"2020","month":"5","Exp_Amt":"23"},{"year":"2020","month":"5","Exp_Amt":"56"},{"year":"2020","month":"5","Exp_Amt":"23"},{"year":"2020","month":"5","Exp_Amt":"56"},{"year":"2020","month":"5","Inc_Amt":"23"},{"year":"2020","month":"5","Inc_Amt":"60"},{"year":"2020","month":"5","Inc_Amt":"23"},{"year":"2020","month":"5","Inc_Amt":"60"},{"year":"2020","month":"5","Inc_Amt":"23"},{"year":"2020","month":"5","Inc_Amt":"23"},{"year":"2020","month":"5","Inc_Amt":"56"},{"year":"2020","month":"5","Inc_Amt":"23"}]
			
			
			$datas = array(
			array('month'=>$month1,'Expense'=>$exp1,'income'=>$inc1,'total'=>$inc1-$exp1),
			array('month'=>$month2,'Expense'=>$exp2,'income'=>$inc2,'total'=>$inc2-$exp2),
			array('month'=>$month3,'Expense'=>$exp3,'income'=>$inc3,'total'=>$inc3-$exp3),
			array('month'=>$month4,'Expense'=>$exp4,'income'=>$inc4,'total'=>$inc4-$exp4),
			array('month'=>$month5,'Expense'=>$exp5,'income'=>$inc5,'total'=>$inc5-$exp5),
			array('month'=>$month6,'Expense'=>$exp6,'income'=>$inc6,'total'=>$inc6-$exp6),
			array('month'=>$month7,'Expense'=>$exp7,'income'=>$inc7,'total'=>$inc7-$exp7),
			array('month'=>$month8,'Expense'=>$exp8,'income'=>$inc8,'total'=>$inc8-$exp8),
			array('month'=>$month9,'Expense'=>$exp9,'income'=>$inc9,'total'=>$inc9-$exp9),
			array('month'=>$month10,'Expense'=>$exp10,'income'=>$inc10,'total'=>$inc10-$exp10),
			array('month'=>$month11,'Expense'=>$exp11,'income'=>$inc11,'total'=>$inc11-$exp11),
			array('month'=>$month12,'Expense'=>$exp12,'income'=>$inc12,'total'=>$inc12-$exp12));
			
		$Total_Value = json_encode($datas);
			
			echo $Total_Value;
			
			/* $all_data = json_encode($datas);
			echo $all_data; */
			//This is the o/t iam getting. Currently I have only may month data in my database.
			//echo $all_data = {"month":"","Expense":0,"income":0}
		}
	}
}
	
?>