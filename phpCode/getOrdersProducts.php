<?php
$uid = addslashes(strip_tags($_GET['uid']));
include "connection.php";
    $sql="SELECT `product`.*,`order`.`orderTotal`,`order_content`.`quantity`,`order_content`.`price` FROM `product`,`order`,`order_content` WHERE `product`.`productID`=`order_content`.`productID`AND `order`.`orderID`=order_content.orderID AND `order`.`userID`=$uid ;";

if ($result = mysqli_query($con,$sql))
  {
   $emparray = array();
   while($row =mysqli_fetch_assoc($result))
       {$emparray[] = $row;}

  echo json_encode($emparray);
  // Free result set
  mysqli_free_result($result);
  mysqli_close($con);
}