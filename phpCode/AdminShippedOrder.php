<?php

    $oid=addslashes(strip_tags($_POST['oid']));

include "connection.php";

$sql = "UPDATE `order` SET `orderState` = 'Shipped' WHERE `order`.`orderID` = $oid;";
mysqli_query($con,$sql);
echo mysqli_error($con);
   mysqli_close($con);
?> 		