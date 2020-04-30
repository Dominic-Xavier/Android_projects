<?php
		require 'conn.php';
		data();
	
	function data(){
		
		global $connection,$count,$query1,$keywords;;
		
		$json = file_get_contents('php://input');
		$obj = json_decode($json,true);
		$myDate = date('Y-m-d');
		try{
			if(!empty($obj)){
				//finding user id
				foreach($obj as $key=> $value){
					$user_id = $value['User_id'];
					break;
				}
				//finding Keyword
				foreach($obj as $key=> $value){
					$keywords = $value['option'];
					break;
				}
				
				$u_id_income = $user_id.$Keyword;
				$u_id_expense = $user_id.$Keyword;
				
				if($keywords == "Expense"){
					$query1 = "insert into $u_id_expense(Date,Description,Amount) values('$myDate','$des','$amt')";
				}
				else{
					$query1 = "insert into $u_id_income(Date,Description,Amount) values('$myDate','$des','$amt')";
				}
				
			//iterating through json object i.e (Des & amt)
			foreach($obj as $key=> $value){
				
				$des = $value['Des'];
				$amt = $value['Amount'];
				
				$query = $query1;
			
		
				if(mysqli_query($connection,$query))
					$count = true;
				else
					$count = False;
				}
	
				if($count == true)
					echo "values inserted";
				else
					echo "Error".mysqli_error($connection);
			}
			else{
				echo"Blank...!";
			}
		}
		catch(Exception $e){
			echo "Error".$e->getMessage();
		}
		mysqli_close($connection);
	}
?>