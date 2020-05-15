<?php
	
	require('conn.php');
	global $connection;
	if(!empty($_POST['User_id']) && !empty($_POST['sdate']) && !empty($_POST['edate'])){
		//here $_POST['sdate'] && $_POST['edate'] is dummy variable.
		$u_id = $_POST['User_id'];
		user_details($u_id,$connection);
	}
	else{
		profile_pic();
	}
	
	
	function user_details($u_id,$connection){
			$query = "select id,Username,Mobile_Number from login where id = '$u_id'";
			$datas = array();
			$result = mysqli_query($connection,$query);
			while($row = mysqli_fetch_assoc($result)){
				$datas[] = $row;
			}
			$json_enc = json_encode($datas);
			echo $json_enc;
		
		
			/* array_push($datas, $row);
			$json = json_encode($json, JSON_PRETTY_PRINT);
			echo $json; */
	}
	
	function profile_pic(){
		echo "Function profile_pic is executed";
	}
?>