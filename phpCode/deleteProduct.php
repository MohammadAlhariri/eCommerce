<?php
$pid = addslashes(strip_tags($_POST['pid']));


include "connection.php";

$sql = "DELETE FROM `product` WHERE `product`.`productID` = $pid;";
mysqli_query($con,$sql);
echo mysqli_error($con);

echo "Record Deleted";
   
mysqli_close($con);
?> 					