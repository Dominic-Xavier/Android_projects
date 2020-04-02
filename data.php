<?php
		require 'conn.php';
		data();
	
	function data(){
		
		global $connection,$count;
		
		$json = file_get_contents('php://input');
		$obj = json_decode($json,true);
		$myDate = date('Y-m-d');
		try{
			if(!empty($obj)){
				
				foreach($obj as $key=> $value){
					$user_id = $value['User_id'];
					break;
				}
		
			foreach($obj as $key=> $value){
				
				$des = $value['Des'];
				$amt = $value['Amount'];
			
				$query = "insert into $user_id(Date,Description,Amount) values('$myDate','$des','$amt')";
			
		
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