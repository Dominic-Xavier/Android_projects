<?php
		require 'conn.php';
		data();
	
	function data(){
		
		global $connection,$count,$query1,$keywords,$table_name,$column_des,$column_amt;
		
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
				
				$table_name = $user_id;
				
				if($keywords == "Expense"){
					$column_des = 'Exp_Des';
					$column_amt = 'Exp_Amt';
				}
				else{
					$column_des = 'Inc_Des';
					$column_amt = 'Inc_Amt';
				}
				
			//iterating through json object i.e (Des & amt)
			foreach($obj as $key=> $value){
				
				$des = $value['Des'];
				$amt = $value['Amount'];
				
				$query = "insert into $table_name(Date,$column_des,$column_amt) values('$myDate','$des','$amt')";
		
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