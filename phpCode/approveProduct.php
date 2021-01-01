<?php
$pid = addslashes(strip_tags($_POST['pid']));


include "connection.php";

$sql = "UPDATE `product` SET `productState` = 'Validate' WHERE `product`.`productID` = $pid;";
mysqli_query($con,$sql);
echo mysqli_error($con);

echo "Record Approved";
   
mysqli_close($con);
?> 				