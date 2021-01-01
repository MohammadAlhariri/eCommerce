<?php
    $name = addslashes(strip_tags($_POST['name']));
    $phone = addslashes(strip_tags($_POST['phone']));
    $city = addslashes(strip_tags($_POST['city']));
    $address = addslashes(strip_tags($_POST['address']));
    $uid=addslashes(strip_tags($_POST['uid']));



include "connection.php";

$sql = "UPDATE `order` SET `orderTotal` = '440', `customerName` = '$name', `customerAddress` = '$address', `customerPhone` = $phone , `customerCity` = '$city' ,`adminApproved`=`Yes` WHERE `order`.`userID` = $uid;";
mysqli_query($con,$sql);
echo mysqli_error($con);

echo "your order will be send soon";
   
mysqli_close($con);
?> 					
<!-- SELECT `product`.`productID`,`product`.`productName`,`product`.`productPrice`,`order`.`orderTotal`,`order_content`.`quantity`,`order_content`.`price` FROM `product`,`order`,`order_content` WHERE `product`.`productID`=`order_content`.`productID`AND `order`.`orderID`=order_content.orderID AND `order`.`userID`=3 -->
<!-- SELECT `product`.`productID`,`product`.`productName`,`product`.`productPrice`,`order`.`userID`,`order`.`orderTotal`,`order_content`.`quantity`,`order_content`.`price` FROM `product`,`order`,`order_content` WHERE `product`.`productID`=`order_content`.`productID`AND `order`.`orderID`=order_content.orderID -->
<!-- UPDATE `order` SET `orderTotal` = '44', `customerName` = '10', `customerAddress` = '10', `customerPhone` = '444', `customerCity` = '444' WHERE `order`.`orderID` = 28 -->
