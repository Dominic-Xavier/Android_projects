<?php
	
	require('conn.php');
	
	// Shortcut key to add comments in notepad++ is CTRL+k
	// Shortcut key to remove comments in notepad++ is CTRL+Shift+k
	
	//$dates=date('d-M-yy');
	//echo $dates;
	
	
	//Sample for DoublyLinkedList in php
	/* $a = new SplDoublyLinkedList;
	$arr=[1,2,3,4,"Xavier",6,7,8,9];

	for($i=0;$i<count($arr);$i++){
		$a->add($i,$arr[$i]);
	}

	print_r($a); */
	
	global $connection;
	
	$query = "select year (date) as year, month (date) as month,  sum(Exp_amt) as Total_expense from u_id_1expense group by year(date), month(date)";
	
	$result = mysqli_query($connection,$query);
	
	$data = array();
	
	while($row = mysqli_fetch_assoc($result)){
		$data[] = $row;
	}
	
	$obj = json_encode($data);
	
	echo $obj;
	
	
?>